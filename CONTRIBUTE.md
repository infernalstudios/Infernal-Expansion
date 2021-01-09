<h1 align="center">Contribution Guide</h1>

## Table of Contents:
 - [Workspace Setup](#workspace-setup)
  - [IntelliJ](#intellij)
  - [Eclipse](#eclipse)
 - [Code Style Guide](#code-style-guide)
 - [Development Guide](#development-guide)
   - [Entities](#entities)

---

## Workspace Setup

For all of these, you'll need:
 - Java SE Development Kit 8: _[Link to OpenJDK Download](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)_
 - Git:  _[Official Download Link](https://git-scm.com/downloads)_
  _Most Linux and MacOS systems have Git installed by default._

### IntelliJ

Prerequesites:
 - IntelliJ IDEA: _[Download Link](https://www.jetbrains.com/idea/download/)_

1. Clone this repository using Git: `git clone https://github.com/nekomaster1000/Infernal-Expansion`
2. Open IntelliJ and select 'open'
3. Navigate to the cloned repository and select 'build.gradle'
4. Open it as a project.
5. You will have to build the project, might take a while.
6. Once you have opened it in IntelliJ, open the terminal. This can be found on the bottom left, and once you click it a small terminal should pop up.
  Here, type `gradlew genIntellijRuns` or `./gradlew genIntellijRuns` if you're on Linux or MacOS. Wait for it to complete.
7. Navigate to the file named InfernalExpansion.java in IntelliJ and double click it to open it.
8. Select the button named 'Edit Configurations' in the top right. you should be taken to a page with two options on the left - Application and Templates;
  Select 'Application', and from the options that appear select 'Run Client'.
9. Return to the project and click the green arrow in the top left, IntelliJ will now compile and run the project.

### Eclipse

Prerequesites:
 - Eclipse IDE: _[Download Link](https://www.eclipse.org/downloads/)_

1. Open Eclipse
2. Navigate to File > Import
3. Choose Git > Projects from Git > Clone URI
4. Under Location, paste `https://github.com/infernalexp/Infernal-Expansion.git` in URI
5. Click next, and skip branch selection
6. Choose a directory where the project files will be stored, click next
7. Choose Import as general project, click next and finish.
8. Open a terminal in your cloned directory and run `gradlew genEclipseRuns` or `./gradlew genEclipseRuns` if you're on Linux or MacOS. Wait for it to complete.
9. Select the project, click 'Run' at the top, and select 'runClient'. Eclipse will now compile and run the project.

## Code Style Guide

This project uses the Google Java Style Guide, with the exception that we use 4 spaces to indent instead of 2: [View Here](https://google.github.io/styleguide/javaguide.html)
Short code examples:

```java
if (condition) {
    doSomething();
} else if (anotherCondition) {
    doAnotherThing();
}
```
```java
public class HelloWorld {
    public HelloWorld(String person) {
        System.out.println(HelloWorld.generateGreeting(person) + "\nHave a lovely day!");
    }

    static String generateGreeting(String person) {
        return "Hello, " + person + "!";
    }
}
```

## Development Guide

### Entities

1. Place your texture in main/resources/assets/infernaldiv/textures/entity

2. Create a new java class in com.nekomaster1000.infernalexp.entities called `MobNameEntity`. Replace `MobName` with your mob name.
    _This is boilerplate code that would normally be in every mob._
    ```java
    public class MobNameEntity extends CreatureEntity {

        protected MobNameEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
            super(type, worldIn);
        }

        public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
            return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
        }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 0.25d));
        }

        @Override
        protected int getExperiencePoints(PlayerEntity player) {
            return 1 + this.world.rand.nextInt(4);
        }

        @Override
        protected SoundEvent getAmbientSound() {
            return SoundEvents.ENTITY_PIG_AMBIENT;
        }

        @Override
        protected SoundEvent getDeathSound() {
            return SoundEvents.ENTITY_PIG_DEATH;
        }

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

3. In `com.nekomaster1000.infernalexp.client.entity.model`, copy and paste your generated class from blockbench.

4. Right click on the file, select 'refactor' and then 'rename', and change it to have the same name as what blockbench gave it.

5. Change the initial class creation line in your model class to:
    _The following is boilerplate code that should be edited accordingly._
    ```java
    public class MobNameModel<T extends MobNameEntity> extends EntityModel<T>
    ```

6. Fix any errors you recieve

7. Refactor it again, changing it to `MobNameModel`, replacing `MobName` with the same as in step 2.

8. Fix any import errors.

9. In the render function, if you have an error in your class, replace `matrixStack`, `buffer`, `packedLight` and `packedOverlay` to have `In` at the end of them. Do the same for any usages of those parameters.

 - The following steps contain boilerplate code that should be edited accordingly:
 
    _Replace any `mob name` according to the syntax/case._
    `mob name` refers to stuff like `MobNameEntity`, `MOB_NAME`, `mob_name`, etc, and should be renamed to something like `GlowsquitoEntity`, `BASALT_GIANT` and `voline`, respectively.

10. In `com.nekomaster1000.infernalexp.init.ModEntityType`, add a new entity. This should be done in the format:
    ```java
    public static final RegistryObject<EntityType<MobNameEntity>> MOB_NAME = ENTITY_TYPES.register("mob_name",
        () -> EntityType.Builder.create(MobNameEntity::new, EntityClassification.CREATURE)
                .size(0.5f, 0.5f)
                .build(new ResourceLocation(InfernalExpansion.MOD_ID, "mob_name").toString()));
    ```

12. In `com.nekomaster1000.infernalexp.client.entity.render`, make a new class, named `MobNameRenderer`, renamed accordingly.
    ```java
    public class MobNameRenderer extends MobRenderer<MobNameEntity, MobNameModel<MobNameEntity>> {

        protected static final ResourceLocation TEXTURE = new ResourceLocation(InfernalExpansion.MOD_ID, "textures/entity/mob_name.png");

        public MobNameRenderer(EntityRendererManager renderManagerIn) {
            super(renderManagerIn, new MobNameModel<>(), 0.7f);
        }

        @Override
        public ResourceLocation getEntityTexture(MobNameEntity entity) {
            return TEXTURE;
        }

    }
    ```

13. In `com.nekomaster1000.infernalexp.InfernalExpansion`, add a new line under `deferredworkqueue.runLater`:
    ```java
    GlobalEntityTypeAttributes.put(ModEntityType.MOB_NAME.get(), MobNameEntity.setCustomAttributes().create());
    ```

14. In `com.nekomaster1000.infernalexp.util.ClientEventBusSubscriber`, add a new line in `onClientSetup()`:
    ```java
    RenderingRegistry.registerEntityRenderingHandler(ModEntityType.MOB_NAME.get(), MobNameRenderer::new);
    ```

15. In `com.nekomaster1000.infernalexp.world.gen.ModEntityPlacement`, add a new line in `spawnPlacement()`:
    ```java
    EntitySpawnPlacementRegistry.register(ModEntityType.MOB_NAME.get(), EntitySpawnPlacementRegistry.PlacementType.[placement-type], Heightmap.Type.[heightmap-type], MobEntity::canSpawnOn);
    ```
    [placement-type] is the possible locations for your mob to spawn:
     - `ON_GROUND`
     - `IN_WATER`
     - `IN_LAVA`
     - `NO_RESTRICTIONS`

    [heightmap-type] is the type of heightmap to use when checking for possible spawn locations:
     - `MOTION_BLOCKING`: The highest block that blocks motion or contains a fluid.
     - `MOTION_BLOCKING_NO_LEAVES`: The highest block that blocks motion or contains a fluid and is not in the `minecraft:leaves` tag.
     - `OCEAN_FLOOR`: The highest non-air block, solid block.
     - `OCEAN_FLOOR_WG`: The highest block that is neither air nor contains a fluid, for worldgen.
     - `WORLD_SURFACE`: The highest non-air block.
     - `WORLD_SURFACE_WG`: The highest non-air block, for worldgen.