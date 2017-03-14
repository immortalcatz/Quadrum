package me.dmillerw.quadrum.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.dmillerw.quadrum.lib.ModInfo;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author dmillerw
 */
public class GuiViewData extends GuiScreen {

    private static final ResourceLocation GUI_BLANK = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/search.png");

    public static interface DataDumper {

        public void addData(List<String> list);
    }

    public static enum Data {

        PARTICLE("particle", (list) -> Arrays.stream(EnumParticleTypes.values()).forEach((e) -> list.add(e.name()))),
        CREATIVE_TAB("creative_tab", (list) -> Arrays.stream(CreativeTabs.CREATIVE_TAB_ARRAY).forEach((e) -> list.add(e.getTabLabel())));

        private String key;
        private DataDumper dataDumper;

        private Data(String key, DataDumper dataDumper) {
            this.key = key;
            this.dataDumper = dataDumper;
        }

        public List<String> getData() {
            List<String> list = Lists.newArrayList();
            this.dataDumper.addData(list);
            return list;
        }

        public static Data fromString(String key) {
            for (Data data : Data.values()) {
                if (data.key.equalsIgnoreCase(key))
                    return data;
            }
            return null;
        }
    }

    private static final int X_SIZE = 176;
    private static final int Y_SIZE = 166;

    private static final int LIST_X = 12;
    private static final int LIST_Y = 31;

    private static final int LIST_X_END = 144;

    private static final int MAX_LINE_COUNT = 14;

    private GuiTextField searchField;

    private GuiUVButton buttonUp;
    private GuiUVButton buttonDown;
    private GuiUVButton buttonBack;

    private int guiLeft;
    private int guiTop;

    private int listOffset = 0;
    private int selectedIndex = -1;

    private long lastClickTime;

    private List<String> listContents = Lists.newArrayList();

    private Data data;
    private boolean mainMenu = true;

    public GuiViewData(String data) {
        if (data == null || data.trim().isEmpty()) {
            this.data = null;
            this.mainMenu = true;
        } else {
            this.data = Data.fromString(data);
            this.mainMenu = false;
        }

        Keyboard.enableRepeatEvents(true);
    }

    public void initGui() {
        super.initGui();

        this.guiLeft = (this.width - X_SIZE) / 2;
        this.guiTop = (this.height - Y_SIZE) / 2;

        this.searchField = new GuiTextField(-1, mc.fontRendererObj, guiLeft + LIST_X, guiTop + 11, LIST_X_END - LIST_X, 20);
        this.searchField.setFocused(true);
        this.searchField.setCanLoseFocus(false);
        this.searchField.setEnableBackgroundDrawing(false);

        this.buttonList.add(buttonUp = new GuiUVButton(0, 153, 26, 176, 14, 14, 14, GUI_BLANK).setTooltip(I18n.translateToLocal(ModInfo.MOD_ID + ":tooltip.scrollUp")));
        this.buttonList.add(buttonDown = new GuiUVButton(1, 153, 46, 176, 28, 14, 14, GUI_BLANK).setTooltip(I18n.translateToLocal(ModInfo.MOD_ID + ":tooltip.scrollDown")));
        this.buttonList.add(buttonBack = new GuiUVButton(2, 153, 143, 176, 56, 14, 14, GUI_BLANK).setTooltip(I18n.translateToLocal(ModInfo.MOD_ID + ":tooltip.back")));

        refresh();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        this.searchField.updateCursorCounter();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partial) {
        scrollMouse(Mouse.getDWheel());

        mc.getTextureManager().bindTexture(GUI_BLANK);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, X_SIZE, Y_SIZE);

        final int minX = guiLeft + LIST_X - 5;
        final int maxX = guiLeft + LIST_X_END + 5;

        for (int i = 0; i < Math.min(listContents.size(), MAX_LINE_COUNT); i++) {
            final int minY = guiTop + LIST_Y + (mc.fontRendererObj.FONT_HEIGHT * i);
            final int maxY = minY + mc.fontRendererObj.FONT_HEIGHT;

            //TODO: Why no work!?
            if (selectedIndex == listOffset + i) {
                GlStateManager.pushMatrix();
                GlStateManager.disableTexture2D();

                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer vertexBuffer = tessellator.getBuffer();

                vertexBuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);

                vertexBuffer.color(51, 51, 51, 255);
                vertexBuffer.pos(minX, maxY, zLevel);
                vertexBuffer.pos(maxX, maxY, zLevel);
                vertexBuffer.pos(maxX, minY, zLevel);
                vertexBuffer.pos(minX, minY, zLevel);

                tessellator.draw();

                GlStateManager.enableTexture2D();
                GlStateManager.popMatrix();
            }

            String string = listContents.get(listOffset + i);
            if (selectedIndex == listOffset + i)
                string = TextFormatting.UNDERLINE + "" + TextFormatting.YELLOW + string + TextFormatting.RESET;

            mc.fontRendererObj.drawString(string, guiLeft + LIST_X, guiTop + LIST_Y + (mc.fontRendererObj.FONT_HEIGHT * i), 0xFFFFFF);
        }

        this.searchField.drawTextBox();

        GlStateManager.pushMatrix();
        GlStateManager.translate(guiLeft, guiTop, 0);
        super.drawScreen(mouseX - guiLeft, mouseY - guiTop, partial);
        GlStateManager.popMatrix();

        for (int i = 0; i < this.buttonList.size(); ++i) {
            GuiButton guiButton = (GuiButton) this.buttonList.get(i);
            if (guiButton instanceof GuiUVButton)
                ((GuiUVButton) guiButton).drawTooltip(mc, mouseX, mouseY);
        }
    }

    private void scrollMouse(int theta) {
        if (theta > 0) {
            listOffset -= 1;
            if (listOffset < 0) listOffset = 0;
        } else if (theta < 0) {
            final int max = Math.max(0, listContents.size() - MAX_LINE_COUNT);
            listOffset += 1;
            if (listOffset > max) listOffset = max;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX - guiLeft, mouseY - guiTop, mouseButton);

        if (mouseButton != 0)
            return;

        final int minX = guiLeft + LIST_X;
        final int maxX = guiLeft + LIST_X_END;

        for (int i = 0; i < Math.min(listContents.size(), MAX_LINE_COUNT); i++) {
            final int minY = guiTop + LIST_Y + (mc.fontRendererObj.FONT_HEIGHT * i);
            final int maxY = minY + mc.fontRendererObj.FONT_HEIGHT;

            if (mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY <= maxY) {
                selectedIndex = listOffset + i;

                if (mainMenu) {
                    long timeSinceLastClick = System.currentTimeMillis() - lastClickTime;

                    if (timeSinceLastClick <= 250) {
                        String text = listContents.get(selectedIndex);

                        this.data = Data.fromString(text);
                        this.mainMenu = false;

                        refresh();
                    }

                    lastClickTime = System.currentTimeMillis();
                }

                return;
            }
        }
    }

    @Override
    protected void keyTyped(char key, int keycode) throws IOException {
        super.keyTyped(key, keycode);

        if (this.searchField.isFocused()) {
            this.searchField.textboxKeyTyped(key, keycode);
            refresh();
        }
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.id == 0) {
            listOffset -= 1;
            if (listOffset < 0) listOffset = 0;
        } else if (guiButton.id == 1) {
            final int max = Math.max(0, listContents.size() - MAX_LINE_COUNT);
            listOffset += 1;
            if (listOffset > max) listOffset = max;
        } else if (guiButton.id == 2) {
            if (this.mainMenu) {
                this.mc.displayGuiScreen((GuiScreen) null);
                if (this.mc.currentScreen == null) {
                    this.mc.setIngameFocus();
                }
            } else {
                this.data = null;
                this.mainMenu = true;

                refresh();
            }
        }
    }

    private void refresh() {
        listOffset = 0;
        selectedIndex = -1;
        listContents.clear();

        Set<String> temporarySet = Sets.newHashSet();

        if (mainMenu) {
            Arrays.stream(Data.values()).forEach((e) -> temporarySet.add(e.key));
        } else {
            if (data == null)
                mc.displayGuiScreen(null);

            for (String string : data.getData()) {
                if (searchField.getText().trim().isEmpty() || string.toLowerCase().contains(searchField.getText())) {
                    temporarySet.add(string);
                }
            }
        }

        listContents.addAll(temporarySet);
    }
}