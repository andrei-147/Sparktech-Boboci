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

        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

            testManualRoti();
        }


    }

    private void testManualRoti()
    {
        if (gamepad1.dpad_up && gamepad1.dpad_left) leftFrontDrive.setPower(speed);
        if (gamepad1.dpad_down && gamepad1.dpad_left) leftBackDrive.setPower(speed);
        if (gamepad1.dpad_up && gamepad1.dpad_right) rightFrontDrive.setPower(speed);
        if (gamepad1.dpad_down && gamepad1.dpad_right) rightBackDrive.setPower(speed);
    }


}
