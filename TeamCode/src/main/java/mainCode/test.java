package mainCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Test Roti", group="Linear OpMode")

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

    private DcMotor rotatieBratIO = null;

    private int DirectieMiscareBrat = 0;
    private double moveSpeed = 1;
    private boolean wasGamepad1upPressed = false;
    private boolean wasGamepad1downPressed = false;
    private boolean wasGamepad1leftPressed = false;
    private boolean wasGamepad1rightPressed = false;

    @Override
    public void runOpMode()
    {
        rotatieBratIO = hardwareMap.get(DcMotor.class, "rotatieBratIO");

        if (rotatieBratIO == null) telemetry.addData("Motors", "rotatieBratIO is null");
        /*
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");

        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);

        // if (rightFrontDrive == null) telemetry.addData("Motors", "RightFront is null");
        if (rightBackDrive == null) telemetry.addData("Motors", "RightBack is null");
        if (leftFrontDrive == null) telemetry.addData("Motors", "LeftFront is null");
        if (leftBackDrive == null) telemetry.addData("Motors", "LeftBack is null");
*/
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();

        while(opModeIsActive())
        {
            DirectieMiscareBrat = 0;
            if (gamepad1.y) DirectieMiscareBrat += 1;
            if (gamepad1.a) DirectieMiscareBrat -= 1;
            rotatieBratIO.setPower(moveSpeed * DirectieMiscareBrat);

            if (gamepad1.dpad_up && !wasGamepad1upPressed) wasGamepad1upPressed = true;
            if (!gamepad1.dpad_up && wasGamepad1upPressed) moveSpeed += 0.1;
            if (!gamepad1.dpad_up) wasGamepad1upPressed = false;

            if (gamepad1.dpad_down && !wasGamepad1downPressed) wasGamepad1downPressed = true;
            if (!gamepad1.dpad_down && wasGamepad1downPressed) moveSpeed -= 0.1;
            if (!gamepad1.dpad_down) wasGamepad1downPressed = false;

            if (moveSpeed < 0.1) moveSpeed = 0.1;
            if (moveSpeed > 1) moveSpeed = 1;

            telemetry.addData("Viteza Miscare Brat", moveSpeed);

            telemetry.update();

            /*
            if (gamepad1.left_stick_x * gamepad1.left_stick_y != 0)
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
*/
        }


    }


}
