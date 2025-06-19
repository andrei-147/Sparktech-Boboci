package mainCode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Drawing;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name="Auto Square 2", group="Test")

public class test2 extends OpMode {
    private Follower follower;
    private final double len = 30;
    private final double offset_x = 20;
    private final double offset_y = 20;
    private final Pose pos1 = new Pose(offset_x, offset_y, Math.toRadians(90));
    private final Pose pos2 = new Pose(offset_x, len + offset_y, Math.toRadians(90));
    private final Pose pos3 = new Pose(len + offset_x, len + offset_y, Math.toRadians(90));
    private final Pose pos4 = new Pose(len + offset_x, offset_y, Math.toRadians(90));
    private int pathState = 0;
    private Path linie1 = new Path(new BezierLine(new Point(pos1), new Point(pos2)));
    private Path linie2 = new Path(new BezierLine(new Point(pos2), new Point(pos3)));
    private Path linie3 = new Path(new BezierLine(new Point(pos3), new Point(pos4)));
    private Path linie4 = new Path(new BezierLine(new Point(pos4), new Point(pos1)));
    private FtcDashboard dash;
    private final TelemetryPacket packet = new TelemetryPacket();
    private PathChain l1, l2, l3, l4;

    private void runFollowerPath() {
        switch (pathState) {
            case 0:
                if (!follower.isBusy()) {
                    follower.followPath(l1);
                    pathState++;
                }
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(l2);
                    pathState++;
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(l3);
                    pathState++;
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(l4);
                    pathState = 0;
                }
                break;
        }
    }

    public void initPaths() {
        l1 = follower.pathBuilder()
                .addPath(linie1)
                .setLinearHeadingInterpolation(pos1.getHeading(), pos2.getHeading())
                .build();
        l2 = follower.pathBuilder()
                .addPath(linie2)
                .setLinearHeadingInterpolation(pos2.getHeading(), pos3.getHeading())
                .build();
        l3 = follower.pathBuilder()
                .addPath(linie3)
                .setLinearHeadingInterpolation(pos3.getHeading(), pos4.getHeading())
                .build();
        l4 = follower.pathBuilder()
                .addPath(linie4)
                .setLinearHeadingInterpolation(pos4.getHeading(), pos1.getHeading())
                .build();
    }

    @Override
    public void init() {
        dash = FtcDashboard.getInstance();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class,LConstants.class);
        follower.setStartingPose(new Pose(pos1.getX(), pos1.getY(), Math.toRadians(90)));

        initPaths();
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        follower.update();
        runFollowerPath();

        dash.getTelemetry().addData("Path State", pathState);
        dash.getTelemetry().addData("Position", follower.getPose().toString());
        dash.getTelemetry().addData("follower busy", follower.isBusy());
        Drawing.drawPath(l1, "aqua");
        Drawing.drawPath(l2, "green");
        Drawing.drawPath(l3, "red");
        Drawing.drawPath(l4, "pink");
        Drawing.drawDebug(follower);
        dash.sendTelemetryPacket(packet);
        dash.getTelemetry().update();
    }
}
