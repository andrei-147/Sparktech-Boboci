package mainCode;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name="Auto Square", group="Test")



public class test extends OpMode {
    private Follower follower;
    private final Pose pos1 = new Pose(0, 0, Math.toRadians(90));
    private final Pose pos2 = new Pose(0, 10, Math.toRadians(90));
    private final Pose pos3 = new Pose(10, 10, Math.toRadians(90));
    private final Pose pos4 = new Pose(10, 0, Math.toRadians(90));
    private int pathState = 0;
    private Path linie1 = new Path(new BezierLine(new Point(pos1), new Point(pos2)));
    private Path linie2 = new Path(new BezierLine(new Point(pos2), new Point(pos3)));
    private Path linie3 = new Path(new BezierLine(new Point(pos3), new Point(pos4)));
    private Path linie4 = new Path(new BezierLine(new Point(pos4), new Point(pos1)));

    private PathChain chain = follower.pathBuilder().addPath(linie1).addPath(linie2).addPath(linie3).addPath(linie4).setLinearHeadingInterpolation(pos1.getHeading(), pos1.getHeading()).build();
    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class,LConstants.class);
        follower.setStartingPose(pos1);
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
        if (!follower.isBusy()) {
            follower.followPath(chain);
        }
        telemetry.addData("Path State", pathState);
        telemetry.addData("Position", follower.getPose().toString());
        telemetry.update();
    }
}
