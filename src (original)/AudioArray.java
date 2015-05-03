import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioArray {

	public static void main(String[] args) {
		String file1 = "beep-1.m4a";
		String file2 = "nino2.m4a";
		AudioArray afiles = new AudioArray();
		// Get the number of audio channels in the wav file
		// int numChannels = file1.getNumChannels();
		byte[] data1 = afiles.readAudioFileData(file1);
		byte[] data2 = afiles.readAudioFileData(file2);

		double[] dData1 = new double[data1.length / 3];
		double[] dData2 = new double[data2.length / 3];

		for (int i = 0, j = 0; i != dData1.length; ++i, j += 3) {
			dData1[i] = (double) ((data1[j] & 0xff)
					| ((data1[j + 1] & 0xff) << 8) | (data1[j + 2] << 16));
			// System.out.println(dData1[i]);
		}
		for (int i = 0, j = 0; i != dData2.length; ++i, j += 3) {
			dData2[i] = (double) ((data2[j] & 0xff)
					| ((data2[j + 1] & 0xff) << 8) | (data2[j + 2] << 16));
			// System.out.println(dData2[i]);
		}
		MannWhitneyUTest mTest = new MannWhitneyUTest();
		double Zvalue = (mTest.mannWhitneyU(dData1, dData2));

		PrintWriter writer;
		try {
			writer = new PrintWriter("the-file-name.txt", "UTF-8");
			writer.println(dData1);
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Zvalue > 5) {
			System.out.println("False");
		} else {
			System.out.println("True");
		}

		// for (int i = 0; i < 1000; i++) {
		// for (int z = 0; z < 10; z++) {
		// System.out.print(data1[i] + " ");
		// }
		// System.out.println();
		// }
		//
		// System.out.format("data len: %d\n", data1.length);
		// System.out.format("data len: %d\n", data2.length);
		// System.out.format("diff len: %d\n", data2.length - data1.length);
	}

	public byte[] readAudioFileData(final String filePath) {
		byte[] data = null;
		try {
			final ByteArrayOutputStream baout = new ByteArrayOutputStream();
			final File file = new File(filePath);
			final AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(file);

			byte[] buffer = new byte[4096];
			int c;
			while ((c = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
				baout.write(buffer, 0, c);
			}
			audioInputStream.close();
			baout.close();
			data = baout.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public byte[] readWAVAudioFileData(final String filePath) {
		byte[] data = null;
		try {
			final ByteArrayOutputStream baout = new ByteArrayOutputStream();
			final AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File(filePath));

			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,
					baout);
			audioInputStream.close();
			baout.close();
			data = baout.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}