// CHECKSTYLE:OFF
package EventMessage;// automatically generated, do not modify

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class EventMessage extends Table {
  public static EventMessage getRootAsEventMessage(ByteBuffer _bb) { return getRootAsEventMessage(_bb, new EventMessage()); }
  public static EventMessage getRootAsEventMessage(ByteBuffer _bb, EventMessage obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public static boolean EventMessageBufferHasIdentifier(ByteBuffer _bb) { return __has_identifier(_bb, "ev42"); }
  public EventMessage __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String sourceName() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer sourceNameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public long messageId() { int o = __offset(6); return o != 0 ? bb.getLong(o + bb_pos) : 0; }
  public long pulseTime() { int o = __offset(8); return o != 0 ? bb.getLong(o + bb_pos) : 0; }
  public long timeOfFlight(int j) { int o = __offset(10); return o != 0 ? (long)bb.getInt(__vector(o) + j * 4) & 0xFFFFFFFFL : 0; }
  public int timeOfFlightLength() { int o = __offset(10); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer timeOfFlightAsByteBuffer() { return __vector_as_bytebuffer(10, 4); }
  public long detectorId(int j) { int o = __offset(12); return o != 0 ? (long)bb.getInt(__vector(o) + j * 4) & 0xFFFFFFFFL : 0; }
   public int detectorIdLength() { int o = __offset(12); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer detectorIdAsByteBuffer() { return __vector_as_bytebuffer(12, 4); }
  public byte facilitySpecificDataType() { int o = __offset(14); return o != 0 ? bb.get(o + bb_pos) : 0; }
  public Table facilitySpecificData(Table obj) { int o = __offset(16); return o != 0 ? __union(obj, o) : null; }

  public static int createEventMessage(FlatBufferBuilder builder,
                                       int source_nameOffset,
                                       long message_id,
                                       long pulse_time,
                                       int time_of_flightOffset,
                                       int detector_idOffset,
                                       byte facility_specific_data_type,
                                       int facility_specific_dataOffset) {
    builder.startObject(7);
    EventMessage.addPulseTime(builder, pulse_time);
    EventMessage.addMessageId(builder, message_id);
    EventMessage.addFacilitySpecificData(builder, facility_specific_dataOffset);
    EventMessage.addDetectorId(builder, detector_idOffset);
    EventMessage.addTimeOfFlight(builder, time_of_flightOffset);
    EventMessage.addSourceName(builder, source_nameOffset);
    EventMessage.addFacilitySpecificDataType(builder, facility_specific_data_type);
    return EventMessage.endEventMessage(builder);
  }

  public static void startEventMessage(FlatBufferBuilder builder) { builder.startObject(7); }
  public static void addSourceName(FlatBufferBuilder builder, int sourceNameOffset) { builder.addOffset(0, sourceNameOffset, 0); }
  public static void addMessageId(FlatBufferBuilder builder, long messageId) { builder.addLong(1, messageId, 0); }
  public static void addPulseTime(FlatBufferBuilder builder, long pulseTime) { builder.addLong(2, pulseTime, 0); }
  public static void addTimeOfFlight(FlatBufferBuilder builder, int timeOfFlightOffset) { builder.addOffset(3, timeOfFlightOffset, 0); }
  public static int createTimeOfFlightVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addInt(data[i]); return builder.endVector(); }
  public static void startTimeOfFlightVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addDetectorId(FlatBufferBuilder builder, int detectorIdOffset) { builder.addOffset(4, detectorIdOffset, 0); }
  public static int createDetectorIdVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addInt(data[i]); return builder.endVector(); }
  public static void startDetectorIdVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addFacilitySpecificDataType(FlatBufferBuilder builder, byte facilitySpecificDataType) { builder.addByte(5, facilitySpecificDataType, 0); }
  public static void addFacilitySpecificData(FlatBufferBuilder builder, int facilitySpecificDataOffset) { builder.addOffset(6, facilitySpecificDataOffset, 0); }
  public static int endEventMessage(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishEventMessageBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset, "ev42"); }
};

