# Infernal-Mod
Infernal Expansion mod


# How to run

Requirements:

Java 8

IntelliJ IDEA


when installing IntelliJ, make sure you added the launchers to PATH

download the folder, unzip it to a chosen location.

Open IntelliJ and select 'open'

navigate to your chosen folder and select 'build.gradle'

Open it as a project.

You will have to build the project, this can take a while if you are on a slow computer (took me half an hour)

Once you have opened it in IntelliJ, open the terminal. This can be found on the bottom left, and once you click it a small terminal should pop up.

Here, type 'gradlew genIntellijRuns'

wait for that to complete

navigate to the file named InfernalExpansion.java in intelliJ and double click it to open it.

select the button named 'edit configurations' in the top right. you should be taken to a page with two options on the left: Application and templates

select application, and from the options that appear select 'run client'

press the 'okay' button.

return to the project and click the green arrow in the top left

enjoy!


DEV GUIDE

# Making a new Entity

Place your texture in main/resources/assets/infernaldiv/textures/entity

create a new java class in com.nekomaster1000.infernalexp.entities called \[your mob\]Entity

Paste the following into your class. This is boilerplate code that would normally be in every mob.

```
public class [ENTITY NAME] extends CreatureEntity {

    protected [ENTITY NAME](EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    //ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    //BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 0.25d));
    }

    //EXP POINTS
    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(4);
    }

    //SOUNDS
    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PIG_AMBIENT; }
    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PIG_DEATH; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

}
```

In com.nekomaster1000.infernalexp.client.entity.model, copy and paste your generated class from blockbench.

right click on the file, select 'refactor' and then 'rename', and change it to have the same name as what blockbench gave it.

change the initial class creation line in your model clas to ```public class [entity]Model<T extends [entity]Entity> extends EntityModel<T>```

fix any errors you recieve

refactor it again, changing it to [your mob]Model

go through the class, hovering over errors and selecting 'import class' on them until there are no more errors

in the render function, if you have an error in your class, matrixStack, buffer, packedLight and packedOverlay to have 'In' at the end of them

do the same for any usages of those parameters

in com.nekomaster1000.infernalexp.init.modEntityType, add a new entity. This should be done in the format:

```
public static final RegistryObject<EntityType<[mob class name]>> [mob name (uppercase)] = ENTITY_TYPES.register("[mob name (lowercase)]",
            () -> EntityType.Builder.create([mob class name]::new, EntityClassification.CREATURE)
                    .size(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(InfernalExpansion.MOD_ID, "[mob name (lowercase)]").toString()));
```

In the above code, \[mob class name] refers to the name of your class, e.g. GlowsquitoEntity

\[mob name (lowercase)] is the name of your texture file, e.g. glowsquito

\[mob name (uppercase)] would be something like GLOWSQUITO

make your entity class public if it is not already

in com.nekomaster1000.infernalexp.client.entity.render, make a new class name [your mob]Renderer

place the following code in your class:

```
public class [file name] extends MobRenderer<[mob class name], [model class name]<[mob class name]>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/[mob name (lowercase)].png");

    public [file name](EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new [model class name]<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture([mob class name] entity) {
        return TEXTURE;
    }
}
```

in com.nekomaster1000.infernalexp.InfernalExpansion.java:

add a new line under deferredworkqueue.runLater

template:

```
GlobalEntityTypeAttributes.put(ModEntityType.[mob name (uppercase)].get(), [mob class name].setCustomAttributes().create());
```

In com.nekomaster1000.infernalexp.util.ClientEventBusSubscriber.java:

add a new line in OnClientSetup

template:

```
RenderingRegistry.registerEntityRenderingHandler(ModEntityType.[entity name(caps)].get(), [entity]Renderer::new);
```


Enjoy your entity! you can change it's behaviour in it's class.
