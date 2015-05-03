import java.nio.ByteBuffer;

public class ByteToDouble {
	private static double readDouble(ByteBuffer buf) {
		return 0;
		// ByteBuffer byteBuffer = new ByteBuffer(buf);
		// int v = (byteBuffer.get() & 0xff);
		// v |= (byteBuffer.get() & 0xff) << 8;
		// v |= byteBuffer.get() << 16;
		// return (double) v;
	}
}
