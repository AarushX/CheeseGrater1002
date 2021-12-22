package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.util.Angle;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.entity.Entity;
import com.noahbres.meepmeep.roadrunner.Constraints;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.Arrays;

import static java.lang.Math.toRadians;

public class SmolBot {


    public static double MAX_VEL = 45;
    public static double MAX_ACCEL = 45;
    public static double MAX_ANG_VEL = toRadians(200);
    public static double MAX_ANG_ACCEL = toRadians(200);
    public static double TRACK_WIDTH = 10;

    public static int HUB_LEVEL = (int) (Math.random() * 3);


    private static final Pose2d blueStartingPosition = new Pose2d(6, 63.5, toRadians(0));
    private static final Pose2d redStartingPosition =
            blueStartingPosition.copy(blueStartingPosition.getX(), -blueStartingPosition.getY(),
                    Angle.normDelta(blueStartingPosition.getHeading() + toRadians(180)));

    public static void main(String[] args) {

        System.setProperty("sun.java2d.opengl", "true");

        MeepMeep mm = new MeepMeep(600);

        RoadRunnerBotEntity blueCycleRoute = new DefaultBotBuilder(mm)
                .setConstraints(MAX_VEL, MAX_ACCEL, MAX_ANG_VEL, MAX_ANG_ACCEL, TRACK_WIDTH)
                .setDimensions(13, 17)
                .setColorScheme(new ColorSchemeBlueDark())
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(blueStartingPosition)
                                .lineToConstantHeading(new Vector2d(-10, 50))
                                .waitSeconds(0.5)
                                .addDisplacementMarker(() -> {System.out.println("Lift out");})
                                .waitSeconds(0.5)
                                .addDisplacementMarker(() -> {System.out.println("Lift in");})
                                .splineToConstantHeading(new Vector2d(15, 64), toRadians(0))
                                .splineToConstantHeading(new Vector2d(50, 64), toRadians(0))
                                .waitSeconds(0.5)
                                .addDisplacementMarker(() -> {System.out.println("Intake");})
                                .setReversed(true)
                                .splineToConstantHeading(new Vector2d(15, 64), toRadians(180))
                                .splineToConstantHeading(new Vector2d(-10, 50), toRadians(-140))
                                .setReversed(false)
                                .build()
                );

/*
        RoadRunnerBotEntity blueCycleRoute = new DefaultBotBuilder(mm)
                .setConstraints(MAX_VEL, MAX_ACCEL, MAX_ANG_VEL, MAX_ANG_ACCEL, TRACK_WIDTH)
                .setDimensions(12, 17)
                .setColorScheme(new ColorSchemeBlueDark())
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(blueStartingPosition)
                                .setReversed(true)
                                .splineTo(new Vector2d(-4.5, 40), toRadians(-112))
                                .waitSeconds(0.3)
                                .setReversed(false)
                                .splineToSplineHeading(new Pose2d(9, 62, toRadians(10)), toRadians(21))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .splineToSplineHeading(new Pose2d(19, 63.5, toRadians(0)), toRadians(0))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .splineTo(new Vector2d(50, 63.5), toRadians(0))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                //Intake
                                .waitSeconds(1.0)
                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(19, 63.5, toRadians(0)), toRadians(180))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .splineToSplineHeading(new Pose2d(9, 62, toRadians(10)), toRadians(-159))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .splineToSplineHeading(new Pose2d(-4.5, 40, toRadians(75)), toRadians(-105))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .setReversed(false)
                                .build()
                );

        RoadRunnerBotEntity redCycleRoute = new DefaultBotBuilder(mm)
                .setConstraints(MAX_VEL, MAX_ACCEL, MAX_ANG_VEL, MAX_ANG_ACCEL, TRACK_WIDTH)
                .setDimensions(12, 17)
                .setColorScheme(new ColorSchemeRedDark())
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(redStartingPosition)
                                .setReversed(true)
                                .splineTo(new Vector2d(-4.5, -40), toRadians(112))
                                .waitSeconds(0.3)
                                .setReversed(false)
                                .splineToSplineHeading(new Pose2d(9, -62, toRadians(-10)), toRadians(-21))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .splineToSplineHeading(new Pose2d(19, -63.5, toRadians(0)), toRadians(0))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .splineTo(new Vector2d(50, -63.5), toRadians(0))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                //Intake
                                .waitSeconds(1.0)
                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(19, -63.5, toRadians(0)), toRadians(180))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .splineToSplineHeading(new Pose2d(9, -62, toRadians(-10)), toRadians(159))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .splineToSplineHeading(new Pose2d(-4.5, -40, toRadians(-75)), toRadians(105))
                                .addDisplacementMarker(() -> System.out.println("marker"))
                                .setReversed(false)
                                .build()
                );

 */


        mm
                .setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setTheme(new ColorSchemeRedDark())
                .setBackgroundAlpha(0.95f)
                .addEntity(blueCycleRoute)
                .start();

    }

    public static TrajectoryVelocityConstraint getVelocityConstraint(double maxVel, double maxAngularVel, double trackWidth) {
        return new MinVelocityConstraint(Arrays.asList(
                new AngularVelocityConstraint(maxAngularVel),
                new MecanumVelocityConstraint(maxVel, trackWidth)
        ));
    }

    public static TrajectoryAccelerationConstraint getAccelerationConstraint(double maxAccel) {
        return new ProfileAccelerationConstraint(maxAccel);
    }
}