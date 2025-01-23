var ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

// Editing the code of methods
var FieldInsnNode = Java.type('org.objectweb.asm.tree.FieldInsnNode');
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
var IntInsnNode = Java.type('org.objectweb.asm.tree.IntInsnNode');
var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');
var TypeInsnNode = Java.type('org.objectweb.asm.tree.TypeInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');

function initializeCoreMod() {
    return {
        'infernalexp_dynamicLighting_shouldKeep': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.world.level.BlockGetter',
                'methodName': 'm_7146_', // getLightEmission
                'methodDesc': '(Lnet/minecraft/core/BlockPos;)I'
            },
            'transformer': transform
        }
    };
}

function transform(method) {
    // label that sits after our custom injection
    var after = new LabelNode();

    var instructions = ASMAPI.listOf(
        // NOTE: false is 0, and IFEQ checks if the value is 0
        // therefore, if we want to check if a value is false, use IFEQ
        // th check if a value is true instead, use IFNE

        // if !DynamicLightingHandler.LIGHT_SOURCES.containsKey(pos) then skip to after
        new FieldInsnNode(Opcodes.GETSTATIC, "org/infernalstudios/infernalexp/client/DynamicLightingHandler", "LIGHT_SOURCES", "Ljava/util/Map;"),
        new VarInsnNode(Opcodes.ALOAD, 1),
        ASMAPI.buildMethodCall("java/util/Map", "containsKey", "(Ljava/lang/Object;)Z", ASMAPI.MethodType.INTERFACE),
        new JumpInsnNode(Opcodes.IFEQ, after),

        // if !DynamicLightingHandler.LIGHT_SOURCES.get(pos).shouldKeep then skip to after
        new FieldInsnNode(Opcodes.GETSTATIC, "org/infernalstudios/infernalexp/client/DynamicLightingHandler", "LIGHT_SOURCES", "Ljava/util/Map;"),
        new VarInsnNode(Opcodes.ALOAD, 1),
        ASMAPI.buildMethodCall("java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", ASMAPI.MethodType.INTERFACE),
        new TypeInsnNode(Opcodes.CHECKCAST, "org/infernalstudios/infernalexp/client/DynamicLightingHandler$LightData"),
        new FieldInsnNode(Opcodes.GETFIELD, "org/infernalstudios/infernalexp/client/DynamicLightingHandler$LightData", "shouldKeep", "Z"),
        new JumpInsnNode(Opcodes.IFEQ, after),

        // return 10
        new IntInsnNode(Opcodes.BIPUSH, 10),
        new InsnNode(Opcodes.IRETURN),

        after
    );

    // insert our instructions at the start of the method
    // this is basically like @Inject(at = @At("HEAD"))
    method.instructions.insertBefore(method.instructions.getFirst(), instructions);

    // because we aren't adding local variable and the largest stack is still 3, we don't need to modify those attributes

    // we're done here
    return method;
}
