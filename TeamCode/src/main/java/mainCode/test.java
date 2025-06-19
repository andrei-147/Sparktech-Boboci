package mainCode;

import java.lang.Math;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Telek test", group="Linear OpMode")

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

    private DcMotor MotorIntakeOuttakeStanga = null;
    private DcMotor MotorIntakeOuttakeDreapta = null;

    private int DirectieMiscareBrat = 0;
    private double moveSpeed = 1;
    private double speed = 1;
    private boolean wasGamepad1upPressed = false;
    private boolean wasGamepad1downPressed = false;
    private boolean wasGamepad1leftPressed = false;
    private boolean wasGamepad1rightPressed = false;

    @Override
    public void runOpMode()
    {
        //MotorIntakeOuttakeStanga = hardwareMap.get(DcMotor.class, "MotorBratStanga");
        //MotorIntakeOuttakeStanga = hardwareMap.get(DcMotor.class, "MotorBratDreapta");

        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");

        //MotorIntakeOuttakeStanga.setDirection(DcMotor.Direction.FORWARD);
        //MotorIntakeOuttakeDreapta.setDirection(DcMotor.Direction.REVERSE);

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
            /* DirectieMiscareBrat = 0;
            if (gamepad1.y) DirectieMiscareBrat += 1;
            if (gamepad1.a) DirectieMiscareBrat -= 1;
            MotorIntakeOuttakeStanga.setPower(moveSpeed * DirectieMiscareBrat);
            MotorIntakeOuttakeDreapta.setPower(moveSpeed * DirectieMiscareBrat); */

            /* if (gamepad1.dpad_up && !wasGamepad1upPressed) wasGamepad1upPressed = true;
            if (!gamepad1.dpad_up && wasGamepad1upPressed) moveSpeed = Math.min(moveSpeed + 0.1, 1);
            if (!gamepad1.dpad_up) wasGamepad1upPressed = false;

            if (gamepad1.dpad_down && !wasGamepad1downPressed) wasGamepad1downPressed = true;
            if (!gamepad1.dpad_down && wasGamepad1downPressed) moveSpeed = Math.max(moveSpeed - 0.1, 0.1);
            if (!gamepad1.dpad_down) wasGamepad1downPressed = false;

            telemetry.addData("Viteza Miscare Brat", moveSpeed); */

            //telemetry.update();


           /* if (gamepad1.left_stick_x * gamepad1.left_stick_y != 0)
            {
                double max; */

                double straight = -gamepad1.left_stick_y;
                double lateral = gamepad1.left_stick_x;
                double orientation = gamepad1.right_stick_x;

                double leftFrontPower = speed * (-straight + lateral + orientation);
                double rightFrontPower = speed * (straight - lateral - orientation);
                double leftBackPower = speed * (straight - lateral + orientation);
                double rightBackPower = speed * (-straight + lateral - orientation);

                /* max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
                max = Math.max(max, Math.abs(leftBackPower));
                max = Math.max(max, Math.abs(rightBackPower));

                 if (max > 1.0) {
                    leftFrontPower /= max;
                    rightFrontPower /= max;
                    leftBackPower /= max;
                    rightBackPower /= max;
                } */

                leftFrontDrive.setPower(leftFrontPower);
                rightFrontDrive.setPower(rightFrontPower);
                leftBackDrive.setPower(leftBackPower);
                rightBackDrive.setPower(rightBackPower);
            }

        }


    }