package mainCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOP Arm", group="Linear OpMode")



public class test extends LinearOpMode {

    private DcMotor MotorIOLeft = null;
    private DcMotor MotorIORight = null;
    private double IOLeftMotorPos = 0;
    private double IORightMotorPos = 0;
    private double IOLeftMotorRevolutions = 0;
    private double IORightMotorRevolutions = 0;
    private double IOLeftMotorAngle = 0;
    private double IORightMotorAngle = 0;
    private double IOLeftMotorAngleNormalized = 0;
    private double IORightMotorAngleNormalized = 0;
    private int ArmMovementDirection = 0;
    private double moveSpeed = 0.3;
    private boolean IsIOArmMoving = false;
    private boolean wasGamepad1upPressed = false;
    private boolean wasGamepad1downPressed = false;
    private boolean wasGamepad1leftPressed = false;
    private boolean wasGamepad1rightPressed = false;
    private boolean wasGamepad1RBPressed = false;
    private boolean wasGamepad1LBPressed = false;
    private boolean wasGamepad1RTPressed = false;
    private boolean wasGamepad1LTPressed = false;
    private boolean wasGamepad1XPressed = false;
    private double Kp = 0;
    private double Ki = 0;
    private double Kd = 0;
    private double TemporaryPosition = 10;
    private double error = 0;
    private double lastError = 0;
    private ElapsedTime timer = null;
    private boolean PLSMOVE = false;
    private double kg = 0;
    private double power = 0;
    private double integralSum = 0;
    private double PID(double pos)
    {
        IsIOArmMoving = true;
        double derivative = 0;

            error = pos - IOLeftMotorPos;

            derivative = (error - lastError) / timer.seconds();

            integralSum += (error + timer.seconds());

            double out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);

            lastError = error;

        IsIOArmMoving = false;
        return out;
    }

    private void CalculatePower() {
//        power = 0;
//
//        if (PLSMOVE && (TemporaryPosition != IOLeftMotorPos)) {
//            if (timer == null) timer = new ElapsedTime();
//            else timer.reset();
//            integralSum = 0;
//            power = PID(TemporaryPosition);
//        }
//
    }
    private void IncrementVariables()
    {
        if (gamepad1.dpad_up && !wasGamepad1upPressed) wasGamepad1upPressed = true;
        if (!gamepad1.dpad_up && wasGamepad1upPressed) Kp += 0.02;
        if (!gamepad1.dpad_up) wasGamepad1upPressed = false;

        if (gamepad1.dpad_down && !wasGamepad1downPressed) wasGamepad1downPressed = true;
        if (!gamepad1.dpad_down && wasGamepad1downPressed) Kp -= 0.02;
        if (!gamepad1.dpad_down) wasGamepad1downPressed = false;

        if (gamepad1.dpad_right && !wasGamepad1rightPressed) wasGamepad1rightPressed = true;
        if (!gamepad1.dpad_right && wasGamepad1rightPressed) Kd += 0.02;
        if (!gamepad1.dpad_right) wasGamepad1rightPressed = false;

        if (gamepad1.dpad_left && !wasGamepad1leftPressed) wasGamepad1leftPressed = true;
        if (!gamepad1.dpad_left && wasGamepad1leftPressed) Kd -= 0.02;
        if (!gamepad1.dpad_left) wasGamepad1leftPressed = false;

        if (gamepad1.right_bumper && !wasGamepad1RBPressed) wasGamepad1RBPressed = true;
        if (!gamepad1.right_bumper && wasGamepad1RBPressed) kg += 0.1;
        if (!gamepad1.right_bumper) wasGamepad1RBPressed = false;

        if (gamepad1.left_bumper && !wasGamepad1LBPressed) wasGamepad1LBPressed = true;
        if (!gamepad1.left_bumper && wasGamepad1LBPressed) kg -= 0.1;
        if (!gamepad1.left_bumper) wasGamepad1LBPressed = false;

        if (gamepad1.left_trigger != 0 && !wasGamepad1RTPressed) wasGamepad1RTPressed = true;
        if (gamepad1.left_trigger == 0 && wasGamepad1RTPressed) TemporaryPosition += 5;
        if (gamepad1.left_trigger == 0) wasGamepad1RTPressed = false;

        if (gamepad1.right_trigger != 0 && !wasGamepad1LTPressed) wasGamepad1LTPressed = true;
        if (gamepad1.right_trigger == 0 && wasGamepad1LTPressed) TemporaryPosition -= 5;
        if (gamepad1.right_trigger == 0) wasGamepad1LTPressed = false;

        if (gamepad1.x && !wasGamepad1XPressed) wasGamepad1XPressed = true;
        if (!gamepad1.x && wasGamepad1XPressed) PLSMOVE = true;
        if (!gamepad1.x) wasGamepad1XPressed = false;
    }

    private void SetVariables()
    {
        IORightMotorPos = MotorIORight.getCurrentPosition();
        IORightMotorRevolutions = IORightMotorPos / 320;
        IORightMotorAngle = IORightMotorRevolutions * 360;
        IORightMotorAngleNormalized = IORightMotorAngle % 360;

        IOLeftMotorPos = MotorIOLeft.getCurrentPosition();
        IOLeftMotorRevolutions = IOLeftMotorPos / 320;
        IOLeftMotorAngle = IOLeftMotorRevolutions * 360;
        IOLeftMotorAngleNormalized = IOLeftMotorAngle % 360;
    }

    private void SetTelemetry() {
        telemetry.addData("KG", kg);
        telemetry.addData("power", power);
        telemetry.addLine();
        telemetry.addData("Position Left Arm", IOLeftMotorPos);
//            telemetry.addData("Revolutions Left Arm", IOLeftMotorRevolutions);
//            telemetry.addData("Angle Left Arm", IOLeftMotorAngle);
//            telemetry.addData("Angle Normalized Left Arm", IOLeftMotorAngleNormalized);
        telemetry.addLine();
        telemetry.addData("Position Left Arm", IOLeftMotorPos);
//            telemetry.addData("Revolutions Left Arm", IOLeftMotorRevolutions);
//            telemetry.addData("Angle Left Arm", IOLeftMotorAngle);
//            telemetry.addData("Angle Normalized Left Arm", IOLeftMotorAngleNormalized);
        telemetry.addLine();
        telemetry.addData("Kp", Kp);
        telemetry.addData("Kd", Kd);
        telemetry.addData("TempPos", TemporaryPosition);
        telemetry.addData("IsMoving", IsIOArmMoving);
        telemetry.addData("PLSMOVE", PLSMOVE);
        if (timer != null) telemetry.addData("timer", timer.seconds());

        telemetry.update();
    }

    @Override
    public void runOpMode()
    {
        MotorIORight = hardwareMap.get(DcMotor.class, "MotorBratDreapta");
        MotorIOLeft = hardwareMap.get(DcMotor.class, "MotorBratStanga");

        IORightMotorPos = MotorIORight.getCurrentPosition();
        IOLeftMotorPos = MotorIOLeft.getCurrentPosition();

        MotorIORight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorIORight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MotorIOLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorIOLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MotorIOLeft.setDirection(DcMotor.Direction.REVERSE);
        MotorIORight.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();

        while(opModeIsActive())
        {
//            ArmMovementDirection = 0;
//            if (gamepad1.y) ArmMovementDirection += 1;
//            if (gamepad1.a) ArmMovementDirection -= 1;
//            MotorIOLeft.setPower(moveSpeed * ArmMovementDirection);
//            MotorIORight.setPower(moveSpeed * ArmMovementDirection);

            IncrementVariables();
            SetVariables();
            CalculatePower();
            SetTelemetry();
        }
    }

}
