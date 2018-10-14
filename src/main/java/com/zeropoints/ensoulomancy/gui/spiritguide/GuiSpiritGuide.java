package com.zeropoints.ensoulomancy.gui.spiritguide;

import java.io.IOException;

import com.zeropoints.ensoulomancy.Main;


import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSpiritGuide extends GuiContainer {

	private static final ResourceLocation PLANK_TEXTURE = new ResourceLocation("textures/blocks/planks_birch.png");
	
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/gui/spirit_guide.png");
	private static final ResourceLocation DECAL_TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/gui/spirit_guide_decals.png");

	public GuiTextArea textArea = null;
	
	public static final int BUTTON_BACK = 0;
	public static final int BUTTON_NEXT = 1;
	public static final int BUTTON_BYE = 2;
	
	
	public GuiSpiritGuide(Container container) {
		super(container);
		textArea = new GuiTextArea(guiLeft - 25, guiTop + 25, 225, 150, "\nWelcome to Ensoulomancy\nI am your spirit guide.\n\nPress the next button for a quick introduction on how to use this book.");
	}

	@Override
    public boolean doesGuiPauseGame() {
    	return true;
    }
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiSpiritGuideMenuButton(BUTTON_BACK, guiLeft + 55, guiTop, 20, 20, "<"));
		buttonList.add(new GuiSpiritGuideMenuButton(BUTTON_NEXT, guiLeft + 100, guiTop, 20, 20, ">"));
		buttonList.add(new GuiSpiritGuideMenuButton(BUTTON_BYE, guiLeft, height - 35, 175, 20, "GOOD BYE"));
    }
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int h = height - 20, w = width - 20;
		
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(PLANK_TEXTURE);
		drawScaledCustomSizeModalRect(10, 10, 0, 0, w/4, h/4, w, h, 16, 16);
		
		mc.getTextureManager().bindTexture(DECAL_TEXTURE);
		int size = 4;
		int textureH = 256 * size,
			textureW = 256 * size,
			dw = 24 * size,
			dh = 30 * size;
		drawModalRectWithCustomSizedTexture(10, 10, 0, 0, dw, dh, textureW, textureH); // Sun
		drawModalRectWithCustomSizedTexture(width - 10 - dw, 10, dw, 0, dw, dh, textureW, textureH); // Moon
		//if (width > 300) {
			drawModalRectWithCustomSizedTexture(width / 2 - 128, 15, 0, 30, 256, 57, 256, 256); // Centered Text
		//}
	}
	
	
	/*
	 * Draw some titles if we want.
	 * 
	 * (non-Javadoc)
	 * @see net.minecraft.client.gui.inventory.GuiContainer#drawGuiContainerForegroundLayer(int, int)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		textArea.DrawTextArea(fontRenderer);
		//String name = I18n.format(ModItems.SPIRIT_GUIDE.getUnlocalizedName() + ".name");
		//fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		//fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}
	
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case BUTTON_BACK:
				System.out.println("back");
				break;
			case BUTTON_NEXT:
				System.out.println("next");
				break;
			case BUTTON_BYE:
				System.out.println("Good Bye!");
				break;
			default:
				break;
		}
    }
	
}
