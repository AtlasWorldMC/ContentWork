package fr.atlasworld.contentwork.api.data.generator.provider;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import fr.atlasworld.contentwork.api.data.generator.FilePipeline;
import fr.atlasworld.contentwork.api.data.model.ModelFile;
import fr.atlasworld.contentwork.api.data.model.builder.ModelBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Generates models for the data generation
 * @param <T> Simply for returning the correct inheritance
 */
public abstract class ModelProvider<T extends ModelBuilder<T>> implements DataProvider {
    public static final String BLOCK_FOLDER = "block";
    public static final String ITEM_FOLDER = "item";

    private final Plugin plugin;
    private final BiFunction<NamespacedKey, FilePipeline, T> factory;
    private final Map<NamespacedKey, T> generatedModels = new HashMap<>();

    public ModelProvider(Plugin plugin, BiFunction<NamespacedKey, FilePipeline, T> factory) {
        this.plugin = plugin;
        this.factory = factory;
    }

    public NamespacedKey minecraftLocation(String name) {
        return NamespacedKey.minecraft(name);
    }

    /**
     * Creates a model with an existing parent
     * @param name name of the model file
     * @param parent the model's parent file
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T withExistingParent(String name, String parent, FilePipeline pipeline) {
        return withExistingParent(name, minecraftLocation(parent), pipeline);
    }

    /**
     * Creates a model with an existing parent
     * @param name name of the model file
     * @param parent the model's parent file
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T withExistingParent(String name, NamespacedKey parent, FilePipeline pipeline) {
        return this.getBuilder(name, pipeline).parent(getExistingFile(parent, pipeline));
    }

    /**
     * Create a cube model
     * @param name name of the model file
     * @param pipeline the file pipeline
     * @param down down texture location
     * @param up up texture location
     * @param north north texture location
     * @param south south texture location
     * @param east east texture location
     * @param west west texture location
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T cube(String name, FilePipeline pipeline, NamespacedKey down, NamespacedKey up, NamespacedKey north, NamespacedKey south, NamespacedKey east, NamespacedKey west) {
        return withExistingParent(name, "cube", pipeline)
                .texture("down", down)
                .texture("up", up)
                .texture("north", north)
                .texture("south", south)
                .texture("east", east)
                .texture("west", west);
    }

    /**
     * Create a model with a single texture
     * @param name name of the model file
     * @param parent the model's parent file
     * @param texture the model's texture location
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    private T singleTexture(String name, String parent, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, minecraftLocation(parent), texture, pipeline);
    }

    /**
     * Create a model with a single texture
     * @param name name of the model file
     * @param parent the model's parent file
     * @param texture the model's texture location
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T singleTexture(String name, NamespacedKey parent, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, parent, "texture", texture, pipeline);
    }

    /**
     * Create a model with a single texture
     * @param name name of the model file
     * @param parent the model's parent file
     * @param textureKey the texture key
     * @param texture the model's texture location
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    private T singleTexture(String name, String parent, String textureKey, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, minecraftLocation(parent), textureKey, texture, pipeline);
    }

    /**
     * Create a model with a single texture
     * @param name name of the model file
     * @param parent the model's parent file
     * @param textureKey the texture key
     * @param texture the model's texture location
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T singleTexture(String name, NamespacedKey parent, String textureKey, NamespacedKey texture, FilePipeline pipeline) {
        return withExistingParent(name, parent, pipeline)
                .texture(textureKey, texture);
    }

    /**
     * Create a cube model with one texture
     * @param name name of the model file
     * @param texture the model's texture location
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T cubeAll(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/cube_all", "all", texture, pipeline);
    }

    /**
     * Create a cube model with a side and top texture being different
     * @param name name of the model file
     * @param side the top texture location
     * @param top the side texture location
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T cubeTop(String name, NamespacedKey side, NamespacedKey top, FilePipeline pipeline) {
        return withExistingParent(name, BLOCK_FOLDER + "/cube_top", pipeline)
                .texture("side", side)
                .texture("top", top);
    }

    /**
     * Create a model with a side, bottom and top texture being different
     * @param name name of the model file
     * @param side the top texture location
     * @param bottom the side texture location
     * @param top the side texture location
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    private T sideBottomTop(String name, String parent, NamespacedKey side, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return withExistingParent(name, parent, pipeline)
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    /**
     * Create a cube model with a side, bottom and top texture being different
     * @param name name of the model file
     * @param side the top texture location
     * @param bottom the side texture location
     * @param top the side texture location
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T cubeBottomTop(String name, NamespacedKey side, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return sideBottomTop(name, BLOCK_FOLDER + "/cube_bottom_top", side, bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T cubeColumn(String name, NamespacedKey side, NamespacedKey end, FilePipeline pipeline) {
        return withExistingParent(name, BLOCK_FOLDER + "/cube_column", pipeline)
                .texture("side", side)
                .texture("end", end);
    }

    @CanIgnoreReturnValue
    public T cubeColumnHorizontal(String name, NamespacedKey side, NamespacedKey end, FilePipeline pipeline) {
        return withExistingParent(name, BLOCK_FOLDER + "/cube_column_horizontal", pipeline)
                .texture("side", side)
                .texture("end", end);
    }

    @CanIgnoreReturnValue
    public T orientableVertical(String name, NamespacedKey side, NamespacedKey front, FilePipeline pipeline) {
        return withExistingParent(name, BLOCK_FOLDER + "/orientable_vertical", pipeline)
                .texture("side", side)
                .texture("front", front);
    }

    @CanIgnoreReturnValue
    public T orientableWithBottom(String name, NamespacedKey side, NamespacedKey front, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return withExistingParent(name, BLOCK_FOLDER + "/orientable_with_bottom", pipeline)
                .texture("side", side)
                .texture("front", front)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    @CanIgnoreReturnValue
    public T orientable(String name, NamespacedKey side, NamespacedKey front, NamespacedKey top, FilePipeline pipeline) {
        return withExistingParent(name, BLOCK_FOLDER + "/orientable", pipeline)
                .texture("side", side)
                .texture("front", front)
                .texture("top", top);
    }

    @CanIgnoreReturnValue
    public T crop(String name, NamespacedKey crop, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/crop", "crop", crop, pipeline);
    }

    @CanIgnoreReturnValue
    public T cross(String name, NamespacedKey cross, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/cross", "cross", cross, pipeline);
    }

    @CanIgnoreReturnValue
    public T stairs(String name, NamespacedKey side, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return sideBottomTop(name, BLOCK_FOLDER + "/stairs", side, bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T stairsOuter(String name, NamespacedKey side, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return sideBottomTop(name, BLOCK_FOLDER + "/outer_stairs", side, bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T stairsInner(String name, NamespacedKey side, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return sideBottomTop(name, BLOCK_FOLDER + "/inner_stairs", side, bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T slab(String name, NamespacedKey side, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return sideBottomTop(name, BLOCK_FOLDER + "/slab", side, bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T slabTop(String name, NamespacedKey side, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return sideBottomTop(name, BLOCK_FOLDER + "/slab_top", side, bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T button(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/button", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T buttonPressed(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/button_pressed", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T buttonInventory(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/button_inventory", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T pressurePlate(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/pressure_plate_up", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T pressurePlateDown(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/pressure_plate_down", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T sign(String name, NamespacedKey texture, FilePipeline pipeline) {
        return getBuilder(name, pipeline).texture("particle", texture);
    }

    @CanIgnoreReturnValue
    public T fencePost(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/fence_post", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T fenceSide(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/fence_side", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T fenceInventory(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/fence_inventory", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T fenceGate(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_fence_gate", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T fenceGateOpen(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_fence_gate_open", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T fenceGateWall(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_fence_gate_wall", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T fenceGateWallOpen(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_fence_gate_wall_open", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T wallPost(String name, NamespacedKey wall, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_wall_post", "wall", wall, pipeline);
    }

    @CanIgnoreReturnValue
    public T wallSide(String name, NamespacedKey wall, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_wall_side", "wall", wall, pipeline);
    }

    @CanIgnoreReturnValue
    public T wallSideTall(String name, NamespacedKey wall, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_wall_side_tall", "wall", wall, pipeline);
    }

    @CanIgnoreReturnValue
    public T wallInventory(String name, NamespacedKey wall, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/wall_inventory", "wall", wall, pipeline);
    }

    @CanIgnoreReturnValue
    private T pane(String name, String parent, NamespacedKey pane, NamespacedKey edge, FilePipeline pipeline) {
        return withExistingParent(name, BLOCK_FOLDER + "/" + parent, pipeline)
                .texture("pane", pane)
                .texture("edge", edge);
    }

    @CanIgnoreReturnValue
    public T panePost(String name, NamespacedKey pane, NamespacedKey edge, FilePipeline pipeline) {
        return pane(name, "template_glass_pane_post", pane, edge, pipeline);
    }

    @CanIgnoreReturnValue
    public T paneSide(String name, NamespacedKey pane, NamespacedKey edge, FilePipeline pipeline) {
        return pane(name, "template_glass_pane_side", pane, edge, pipeline);
    }

    @CanIgnoreReturnValue
    public T paneSideAlt(String name, NamespacedKey pane, NamespacedKey edge, FilePipeline pipeline) {
        return pane(name, "template_glass_pane_side_alt", pane, edge, pipeline);
    }

    @CanIgnoreReturnValue
    public T paneNoSide(String name, NamespacedKey pane, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_glass_pane_noside", "pane", pane, pipeline);
    }

    @CanIgnoreReturnValue
    public T paneNoSideAlt(String name, NamespacedKey pane, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_glass_pane_noside_alt", "pane", pane, pipeline);
    }

    @CanIgnoreReturnValue
    private T door(String name, String model, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return withExistingParent(name, BLOCK_FOLDER + "/" + model, pipeline)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    @CanIgnoreReturnValue
    public T doorBottomLeft(String name, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return door(name, "door_bottom_left", bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T doorBottomLeftOpen(String name, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return door(name, "door_bottom_left_open", bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T doorBottomRight(String name, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return door(name, "door_bottom_right", bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T doorBottomRightOpen(String name, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return door(name, "door_bottom_right_open", bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T doorTopLeft(String name, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return door(name, "door_top_left", bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T doorTopLeftOpen(String name, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return door(name, "door_top_left_open", bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T doorTopRight(String name, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return door(name, "door_top_right", bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T doorTopRightOpen(String name, NamespacedKey bottom, NamespacedKey top, FilePipeline pipeline) {
        return door(name, "door_top_right_open", bottom, top, pipeline);
    }

    @CanIgnoreReturnValue
    public T trapdoorBottom(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_trapdoor_bottom", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T trapdoorTop(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_trapdoor_top", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T trapdoorOpen(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_trapdoor_open", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T trapdoorOrientableBottom(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_orientable_trapdoor_bottom", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T trapdoorOrientableTop(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_orientable_trapdoor_top", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T trapdoorOrientableOpen(String name, NamespacedKey texture, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_orientable_trapdoor_open", texture, pipeline);
    }

    @CanIgnoreReturnValue
    public T torch(String name, NamespacedKey torch, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_torch", "torch", torch, pipeline);
    }

    @CanIgnoreReturnValue
    public T torchWall(String name, NamespacedKey torch, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/template_torch_wall", "torch", torch, pipeline);
    }

    @CanIgnoreReturnValue
    public T carpet(String name, NamespacedKey wool, FilePipeline pipeline) {
        return singleTexture(name, BLOCK_FOLDER + "/carpet", "wool", wool, pipeline);
    }

    /**
     * Creates a model builder
     * @param path the path of the model
     * @param pipeline the file pipeline
     * @return model builder
     */
    @CanIgnoreReturnValue
    public T getBuilder(@NotNull String path, @NotNull FilePipeline pipeline){
        Preconditions.checkNotNull(path, "Path must not be null!");
        Preconditions.checkNotNull(pipeline, "Pipeline cannot be null!");

        NamespacedKey outLocation = path.contains(":") ? NamespacedKey.fromString(path) : new NamespacedKey(this.plugin, path);
        return this.generatedModels.computeIfAbsent(outLocation, key -> this.factory.apply(key, pipeline));
    }

    /**
     * Creates a model file for an existing model file
     * @param key model file location
     * @param pipeline the file pipeline
     * @return existing model file
     */
    @CanIgnoreReturnValue
    public ModelFile.ExistingModelFile getExistingFile(NamespacedKey key, FilePipeline pipeline) {
        ModelFile.ExistingModelFile model = new ModelFile.ExistingModelFile(key, pipeline);
        model.assertExistence();
        return model;
    }

    /**
     * Starts the generation of the models
     * @param pipeline the file pipeline
     */
    @Override
    @SuppressWarnings("unchecked")
    public void run(FilePipeline pipeline) {
        this.generate(pipeline);
        pipeline.saveModels((Map<NamespacedKey, ModelBuilder<?>>) this.generatedModels);
    }
}
