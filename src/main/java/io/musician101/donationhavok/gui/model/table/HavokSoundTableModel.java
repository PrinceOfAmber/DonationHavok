package io.musician101.donationhavok.gui.model.table;

import io.musician101.donationhavok.handler.havok.HavokSound;
import java.util.List;
import net.minecraft.util.ResourceLocation;

public class HavokSoundTableModel extends ListTableModel<HavokSound> {

    public HavokSoundTableModel(List<HavokSound> elements) {
        super(elements, "Delay", "X Offset", "Y Offset", "Z Offset", "Pitch", "Volume", "Sound");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HavokSound sound = elements.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return sound.getDelay();
            case 1:
                return sound.getXOffset();
            case 2:
                return sound.getYOffset();
            case 3:
                return sound.getZOffset();
            case 4:
                return sound.getPitch();
            case 5:
                return sound.getVolume();
            case 6:
                ResourceLocation rl = sound.getSoundEvent().getRegistryName();
                return rl == null ? "null" : rl.toString();
            default:
                return null;
        }
    }
}
