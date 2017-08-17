package Image;// automatically generated, do not modify

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class PulseImage extends Table {
  public static PulseImage getRootAsPulseImage(ByteBuffer _bb) { return getRootAsPulseImage(_bb, new PulseImage()); }
  public static PulseImage getRootAsPulseImage(ByteBuffer _bb, PulseImage obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public static boolean PulseImageBufferHasIdentifier(ByteBuffer _bb) { return __has_identifier(_bb, "ai34"); }
  public PulseImage __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public long pulseTime() { int o = __offset(4); return o != 0 ? bb.getLong(o + bb_pos) : 0; }
  public long detectorId(int j) { int o = __offset(6); return o != 0 ? (long)bb.getInt(__vector(o) + j * 4) & 0xFFFFFFFFL : 0; }
  public int detectorIdLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer detectorIdAsByteBuffer() { return __vector_as_bytebuffer(6, 4); }
  public long detectionCount(int j) { int o = __offset(8); return o != 0 ? (long)bb.getInt(__vector(o) + j * 4) & 0xFFFFFFFFL : 0; }
  public int detectionCountLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer detectionCountAsByteBuffer() { return __vector_as_bytebuffer(8, 4); }

  public static int createPulseImage(FlatBufferBuilder builder,
      long pulse_time,
      int detector_idOffset,
      int detection_countOffset) {
    builder.startObject(3);
    PulseImage.addPulseTime(builder, pulse_time);
    PulseImage.addDetectionCount(builder, detection_countOffset);
    PulseImage.addDetectorId(builder, detector_idOffset);
    return PulseImage.endPulseImage(builder);
  }

  public static void startPulseImage(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addPulseTime(FlatBufferBuilder builder, long pulseTime) { builder.addLong(0, pulseTime, 0); }
  public static void addDetectorId(FlatBufferBuilder builder, int detectorIdOffset) { builder.addOffset(1, detectorIdOffset, 0); }
  public static int createDetectorIdVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addInt(data[i]); return builder.endVector(); }
  public static void startDetectorIdVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addDetectionCount(FlatBufferBuilder builder, int detectionCountOffset) { builder.addOffset(2, detectionCountOffset, 0); }
  public static int createDetectionCountVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addInt(data[i]); return builder.endVector(); }
  public static void startDetectionCountVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endPulseImage(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishPulseImageBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset, "ai34"); }
};

