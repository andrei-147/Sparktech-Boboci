package mainCode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import mainCode.Componente.Sasiu;
import mainCode.Componente.Brat;

@TeleOp(name="TeleOP Full", group="Linear OpMode")

public class TeleOP_full extends LinearOpMode {
    private static Sasiu sasiu = new Sasiu();
    private static Brat brat = new Brat();

    private static MultipleTelemetry tel;

    @Override

    public void runOpMode() {

        tel =  new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        sasiu.InitializeComponents(gamepad1, hardwareMap);
        brat.InitializeComponents(gamepad1, hardwareMap, tel);

        waitForStart();

        while (opModeIsActive()) {
            sasiu.RunInsideLoop();
            brat.RunInsideLoop();
        }
    }
}