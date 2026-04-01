package net.project.vectorpoint;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AimModule {
    public static boolean enabled = false;
    public static float range = 30.0f;
    public static float fov = 90.0f;
    public static float strength = 0.15f; 
    public static boolean targetHead = true;

    public static void tick(MinecraftClient client) {
        if (!enabled || client.player == null || client.world == null) return;

        PlayerEntity target = null;
        double closestAngle = fov / 2;

        for (PlayerEntity entity : client.world.getPlayers()) {
            if (entity == client.player || !entity.isAlive() || entity.isInvisible()) continue;

            // 1. Range Check (Distance)
            if (client.player.distanceTo(entity) > range) continue;

            // 2. Math to find rotations
            float[] rotations = getRotations(client, entity);
            float yawDiff = Math.abs(MathHelper.wrapDegrees(rotations[0] - client.player.getYaw()));

            // 3. FOV Check
            if (yawDiff < closestAngle) {
                closestAngle = yawDiff;
                target = entity;
            }
        }

        if (target != null) {
            float[] rotations = getRotations(client, target);
            
            // LERP Equation: Current + (Target - Current) * Strength
            float newYaw = MathHelper.lerpAngleDegrees(strength, client.player.getYaw(), rotations[0]);
            float newPitch = MathHelper.lerp(strength, client.player.getPitch(), rotations[1]);

            client.player.setYaw(newYaw);
            client.player.setPitch(newPitch);
        }
    }

    private static float[] getRotations(MinecraftClient client, PlayerEntity target) {
        Vec3d targetPos = targetHead ? target.getEyePos() : target.getPos().add(0, 1.0, 0);
        Vec3d diff = targetPos.subtract(client.player.getEyePos());
        double diffXZ = Math.sqrt(diff.x * diff.x + diff.z * diff.z);

        float yaw = (float) Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diff.y, diffXZ));
        return new float[]{yaw, pitch};
    }
}