package org.firstinspires.ftc.teamcode.opModes

import com.acmerobotics.roadrunner.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

import org.firstinspires.ftc.teamcode.MecanumDrive

@TeleOp(name = "Cassius Thundercock")
class RedSpyInTheBase: LinearOpMode() {

    lateinit var drive: MecanumDrive
    lateinit var launcher: Utils.Launcher

    override fun runOpMode() {
        drive = MecanumDrive(hardwareMap, Pose2d(0.0, 0.0, 0.0))

        waitForStart()


    }

}