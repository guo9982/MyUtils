/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.mytest.spark.protocol;

import java.util.Objects;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* Encapsulates a request for a particular chunk of a stream.
*/
public final class StreamChunkId implements Encodable {
  public final long streamId;
  public final int chunkIndex;

  public StreamChunkId(long streamId, int chunkIndex) {
    this.streamId = streamId;
    this.chunkIndex = chunkIndex;
  }

  @Override
  public int encodedLength() {
    return 8 + 4;
  }

  public void encode(ByteBuf buffer) {
    buffer.writeLong(streamId);
    buffer.writeInt(chunkIndex);
  }

  public static StreamChunkId decode(ByteBuf buffer) {
    assert buffer.readableBytes() >= 8 + 4;
    long streamId = buffer.readLong();
    int chunkIndex = buffer.readInt();
    return new StreamChunkId(streamId, chunkIndex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(streamId, chunkIndex);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof StreamChunkId) {
      StreamChunkId o = (StreamChunkId) other;
      return streamId == o.streamId && chunkIndex == o.chunkIndex;
    }
    return false;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("streamId", streamId)
      .append("chunkIndex", chunkIndex)
      .toString();
  }
}
