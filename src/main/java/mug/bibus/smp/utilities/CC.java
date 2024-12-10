package mug.bibus.smp.utilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class CC {
   public static final TextColor PRIMARY = TextColor.color(255, 192, 203);
   public static final TextColor RED = TextColor.color(160, 0, 3);
   public static final Component GRACE_PERIOD_MESSAGE = Component
           .text("Grace Period is currently active, during Grace Period you are unable to hit other players.")
           .color(RED);
}