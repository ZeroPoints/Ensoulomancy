package com.zeropoints.ensoulomancy.gui.spiritguide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.init.ModItems;
import com.zeropoints.ensoulomancy.util.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSpiritGuide extends GuiContainer {

	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/spirit_guide.png");

	
	public GuiTextArea textArea = null;
	
	public static final int BUTTON_0 = 0;
	public static final int BUTTON_1 = 1;
	public static final int BUTTON_2 = 2;
	
	
	public GuiSpiritGuide(Container container) {
		super(container);
		
		textArea = new GuiTextArea(guiLeft, guiTop, 200, 150, "texty face long snot potato super doooper iceypole food to eat more text. plz keep going with more text everywhere");
		
	}

	
	@Override
	public void initGui()
    {
		super.initGui();
		buttonList.add(new GuiSpiritGuideMenuButton(999901, guiLeft, guiTop, 20, 20, "<"));
		buttonList.add(new GuiSpiritGuideMenuButton(999902, guiLeft, guiTop+30, 80, 20, "texty face"));
		buttonList.add(new GuiSpiritGuideMenuButton(999903, guiLeft-50, guiTop+60, 250, 20, "texty face long snot potato"));
		
		
		

    }
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(BG_TEXTURE);
		//int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight
		//First 2 is screen top left corner offset
		//UV values would be texture offsets
		//Second last 2 are scale to stretch image over screen
		//Last 2 i have no idea. Seems to act strange with 1 and repeats the image in same area. at larger number then image.
		//Main.log("x:" + xSize +  ". y:"+ySize);
		//Main.log("w:" + width  +  ". h:"+height );
		//Main.log("w:" + mc.displayWidth  +  ". h:"+mc.displayHeight );
		
		drawScaledCustomSizeModalRect(10, 20, 0, 0, 256, 256, width-20, height-40, 256, 256);


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
	protected void actionPerformed(GuiButton button) throws IOException
    {
		Main.log("act perf");
		switch(button.id) {
		case BUTTON_0:
			
			break;
		case BUTTON_2:
					
					break;
		case BUTTON_1:
			
			break;
		default:
		
			break;
		}
    }
	
}
