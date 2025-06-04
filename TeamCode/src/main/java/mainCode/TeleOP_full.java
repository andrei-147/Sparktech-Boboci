package mainCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import mainCode.Componente.Sasiu;
import mainCode.Componente.Brat;

@TeleOp(name="TeleOP Full", group="Linear OpMode")

public class TeleOP_full extends LinearOpMode {
    private static Sasiu sasiu = new Sasiu();
    private static Brat brat = new Brat();

    @Override

    public void runOpMode() {

        sasiu.InitializeComponents(gamepad1, hardwareMap);
        brat.InitializeComponents(gamepad1, hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            sasiu.RunInsideLoop();
            brat.RunInsideLoop();
        }
    }
}