package mainCode.Componente;
import java.lang.Math;
import java.math.BigDecimal;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;

public class Brat {
    private Gamepad gamepad1 = null;
    private Telemetry telemetry = null;

    // Motoare si pozitii motoare
    private DcMotor MotorIOLeft = null;
    private DcMotor MotorIORight = null;
    private double IOLeftMotorPos = 0;
    private double IORightMotorPos = 0;

    // Valori brat
    private double moveSpeed = 0.5;
    private boolean IsIOArmMoving = false;
    private double power = 0;

    // Variabile PID
    private double D = 0;
    private double P = 0;
    private double error = 0;
    private BigDecimal Kp = new BigDecimal("0.0080");
    private BigDecimal Ki = new BigDecimal("0");
    private BigDecimal Kd = new BigDecimal("0.0036");
    private double TemporaryPosition = 10;
    private double lastError = 0;
    private ElapsedTime timer = null;
    private boolean PLSMOVE = false;
    private double integralSum = 0;

    // Booleanuri
    private boolean wasGamepad1upPressed = false;
    private boolean wasGamepad1downPressed = false;
    private boolean wasGamepad1leftPressed = false;
    private boolean wasGamepad1rightPressed = false;
    private boolean wasGamepad1RBPressed = false;
    private boolean wasGamepad1LBPressed = false;
    private boolean wasGamepad1YPressed = false;
    private boolean wasGamepad1APressed = false;
    private boolean wasGamepad1XPressed = false;

    private double PID(double pos)
    {
        error = pos - IOLeftMotorPos;

        double derivative = (error - lastError) / timer.seconds();

        integralSum += (error + timer.seconds());

        D = (Kd.doubleValue() * derivative);
        P = (Kp.doubleValue() * error);

        double out = P + /*(Ki.doubleValue() * integralSum)*/ + D;

        lastError = error;
        //if (out < 0) out /= 2;
        return out;
    }
    private void IncrementVariables()
    {
        if (gamepad1.dpad_up && !wasGamepad1upPressed) wasGamepad1upPressed = true;
        if (!gamepad1.dpad_up && wasGamepad1upPressed) Kp = Kp.add(new BigDecimal("0.0002"));
        if (!gamepad1.dpad_up) wasGamepad1upPressed = false;

        if (gamepad1.dpad_down && !wasGamepad1downPressed) wasGamepad1downPressed = true;
        if (!gamepad1.dpad_down && wasGamepad1downPressed) Kp = Kp.add(new BigDecimal("-0.0002"));
        if (!gamepad1.dpad_down) wasGamepad1downPressed = false;

        if (gamepad1.dpad_right && !wasGamepad1rightPressed) wasGamepad1rightPressed = true;
        if (!gamepad1.dpad_right && wasGamepad1rightPressed) Kd = Kd.add(new BigDecimal("0.0002"));
        if (!gamepad1.dpad_right) wasGamepad1rightPressed = false;

        if (gamepad1.dpad_left && !wasGamepad1leftPressed) wasGamepad1leftPressed = true;
        if (!gamepad1.dpad_left && wasGamepad1leftPressed) Kd = Kd.add(new BigDecimal("-0.0002"));
        if (!gamepad1.dpad_left) wasGamepad1leftPressed = false;

        if (gamepad1.y && !wasGamepad1YPressed) wasGamepad1YPressed = true;
        if (!gamepad1.y && wasGamepad1YPressed) TemporaryPosition += 5;
        if (!gamepad1.y) wasGamepad1YPressed = false;

        if (gamepad1.a && !wasGamepad1APressed) wasGamepad1APressed = true;
        if (!gamepad1.a && wasGamepad1APressed) TemporaryPosition -= 5;
        if (!gamepad1.a) wasGamepad1APressed = false;

        if (gamepad1.right_bumper && !wasGamepad1RBPressed) wasGamepad1RBPressed = true;
        if (!gamepad1.right_bumper && wasGamepad1RBPressed) moveSpeed += 0.05;
        if (!gamepad1.right_bumper) wasGamepad1RBPressed = false;

        if (gamepad1.left_bumper && !wasGamepad1LBPressed) wasGamepad1LBPressed = true;
        if (!gamepad1.left_bumper && wasGamepad1LBPressed) moveSpeed -= 0.05;
        if (!gamepad1.left_bumper) wasGamepad1LBPressed = false;

        if (gamepad1.x && !wasGamepad1XPressed) wasGamepad1XPressed = true;
        if (!gamepad1.x && wasGamepad1XPressed) {
            IsIOArmMoving = !IsIOArmMoving;
            if (IsIOArmMoving) {
                if (timer == null) timer = new ElapsedTime();
                else timer.reset();
            }
        }
        if (!gamepad1.x) wasGamepad1XPressed = false;
    }

    private void SetVariables()
    {
        IORightMotorPos = MotorIORight.getCurrentPosition();
//        IORightMotorRevolutions = IORightMotorPos / 320;
//        IORightMotorAngle = IORightMotorRevolutions * 360;
//        IORightMotorAngleNormalized = IORightMotorAngle % 360;

        IOLeftMotorPos = MotorIOLeft.getCurrentPosition();
//        IOLeftMotorRevolutions = IOLeftMotorPos / 320;
//        IOLeftMotorAngle = IOLeftMotorRevolutions * 360;
//        IOLeftMotorAngleNormalized = IOLeftMotorAngle % 360;
    }

    private void SetTelemetry() {
        telemetry.addData("power", power);
        telemetry.addData("Mspeed", moveSpeed);
        telemetry.addLine();
        telemetry.addData("Position Right Arm", IORightMotorPos);
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
        telemetry.addData("Ki", Ki);
        telemetry.addData("Kd", Kd);
        telemetry.addData("TempPos", TemporaryPosition);
        telemetry.addData("IsMoving", IsIOArmMoving);
        telemetry.addData("error", error);
        telemetry.addData("P", P);
        telemetry.addData("D", D);
        if (timer != null) telemetry.addData("timer", timer.seconds());

        telemetry.update();
    }

    public void InitializeComponents(Gamepad g1, HardwareMap hw_map, Telemetry tel) {
        telemetry = tel;
        gamepad1 = g1;
        MotorIORight = hw_map.get(DcMotor.class, "MotorBratDreapta");
        MotorIOLeft = hw_map.get(DcMotor.class, "MotorBratStanga");

        IORightMotorPos = MotorIORight.getCurrentPosition();
        IOLeftMotorPos = MotorIOLeft.getCurrentPosition();

        MotorIORight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorIORight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MotorIOLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorIOLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MotorIORight.setDirection(DcMotor.Direction.FORWARD);
        MotorIOLeft.setDirection(DcMotor.Direction.REVERSE);

        MotorIOLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorIORight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void RunInsideLoop() {
        IncrementVariables();
        SetVariables();

        if (IsIOArmMoving) power = PID(TemporaryPosition);
        else power = 0;

        moveSpeed = Math.min(Math.max(0, moveSpeed), 1);
        power = Math.min(Math.max(-1, power), 1);

        MotorIORight.setPower(moveSpeed * power);
        MotorIOLeft.setPower(moveSpeed * power);
        SetTelemetry();
    }
}
