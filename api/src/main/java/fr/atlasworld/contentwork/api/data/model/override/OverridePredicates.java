package fr.atlasworld.contentwork.api.data.model.override;

public enum OverridePredicates {
    /**
     * Used on compasses to determine the current angle, expressed in a decimal value of less than one.
     */
    ANGLE,

    /**
     * Used on shields to determine if currently blocking. If 1, the player is blocking.
     */
    BLOCKING,

    /**
     * Used on ender pearls and chorus fruit to determine the remaining cooldown, expressed in a decimal value between 0 and 1.
     */
    COOLDOWN,

    /**
     * Used on items with durability to determine the amount of damage, expressed in a decimal value between 0 and 1.
     */
    DAMAGE,

    /**
     * Used on items with durability to determine if it is damaged. If 1, the item is damaged. Note that if an item has the unbreakable tag, this may be 0 while the item has a non-zero "damage" tag.
     */
    DAMAGED,

    /**
     *  Determines the model used by left handed players. It affects the item they see in inventories, along with the item players see them holding or wearing.
     */
    LEFT_HANDED,

    /**
     * Determines the amount a bow or crossbow has been pulled, expressed in a decimal value of less than one.
     */
    PULL,

    /**
     * Used on bows and crossbows to determine if the bow is being pulled. If 1, the bow is currently being pulled.
     */
    PULLING,

    /**
     * Used on crossbows to determine if they are charged with any projectile. If 1, the crossbow is charged.
     */
    CHARGED,

    /**
     *  Used on crossbows. If 1, the crossbow is charged with a firework rocket.
     */
    FIREWORK,

    /**
     * Used on tridents to determine if the trident is ready to be thrown by the player. If 1, the trident is ready for fire.
     */
    THROWING,

    /**
     * Used on clocks to determine the current time, expressed in a decimal value of less than one.
     */
    TIME,

    /**
     * Used on any item and is compared to the tag.CustomModelData NBT, expressed in an integer value. The number is still internally converted to float, causing a precision loss for some numbers above 16 million. If the value read from the item data is greater than or equal to the value used for the predicate, the predicate is positive.
     */
    CUSTOM_MODEL_DATA,

    /**
     * Used on light blocks to determine the light level as contained in BlockStateTag, expressed in a decimal value between 0 and 1, where 1 is light level 15.
     */
    LEVEL,

    /**
     * Used on bundles to determine the ratio of the bundle's current contents to its total capacity, expressed in a decimal value between 0 and 1.
     */
    FILLED,

    /**
     * Used on goat horns to determine whether the player is tooting them. 1 for true, 0 for false.
     */
    TOOTING,

    /**
     * Used on armor to determine which material the applied trim is made of, expressed in a decimal value between 0 and 1.
     */
    TRIM_TYPE,

    /**
     * Used on brushes to determine the brushing animation progress, expressed in a decimal value between 0 and 1.
     */
    BRUSHING
}
