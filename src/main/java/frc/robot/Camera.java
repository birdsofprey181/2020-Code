package frc.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera extends Thread {
	//Public non-changable variables telling Camera dimensions.
	public static final int IMG_WIDTH = 240;
	public static final int IMG_HEIGHT = 480;
	
	private String streamname;
	private UsbCamera camera;
	
	public Camera(String streamname) {
		this.streamname = streamname;
		camera = CameraServer.getInstance().startAutomaticCapture();
	}
	
	public void run() {
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

		CvSink cvSink = CameraServer.getInstance().getVideo();
		CvSource outputStream = CameraServer.getInstance().putVideo(streamname, 640, 480);

		Mat mat = new Mat();
		
		while (!Thread.interrupted()) {
			if (cvSink.grabFrame(mat) == 0) {
				outputStream.notifyError(cvSink.getError());
				continue;
			}
			Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
					new Scalar(255, 255, 255), 5);
			outputStream.putFrame(mat);
		}
		this.setDaemon(true);
	}
	
	public UsbCamera getCamera() {
		return this.camera;
	}
}