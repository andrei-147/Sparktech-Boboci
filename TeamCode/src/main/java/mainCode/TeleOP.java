package mainCode;

import java.lang.Math;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOP Sasiu", group="Linear OpMode")

/*
 * TO-DO LIST:
 *
 * - sa modificam codul astfel incat sa respecte toate conventiile
 * */


public class test extends LinearOpMode {

    private DcMotor rightFrontDrive = null;
    private DcMotor leftFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor leftBackDrive = null;
    private double speed = 1;

    @Override
    public void runOpMode()
    {
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");

        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();

        while(opModeIsActive())
        {
            double max;

            double straight = -gamepad1.left_stick_y;
            double lateral = gamepad1.left_stick_x;
            double orientation = gamepad1.right_stick_x;

            double leftFrontPower = speed * (straight + lateral + orientation);
            double rightFrontPower = speed * (straight - lateral - orientation);
            double leftBackPower = speed * (straight - lateral + orientation);
            double rightBackPower = speed * (straight + lateral - orientation);

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);
        }


    }


}
