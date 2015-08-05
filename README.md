     rpc-ts is a java rpc system which based on the dubbo of alibaba. But the underlying serialization framework, combined with google's own protobuf, facebook of thrift, and the fastest serialization framework written in java field kryo a serialization framework varint serialization, and dubbo and protobuf in Ascii character, enum type, protobuf (var int, long) and so do some improvements, in a circular reference, the sequence of transmission of the remote object (duplicate object local, remote serialization), the id of a variable length write fully qualified class name in lieu of writing class optimize the byte stream as small as possible.
     In the RPC client skeleton, the server stub aspect, the continuation of the dubbo use javassist dynamic bytecode generation technology, and several bugg dubbo made changes. Himself has tried to complete the dynamic reference reflect asm byte code generation,
However, because the code is too big, and no javassist package put together a string of convenience to give up.
     At the same time in order to balance the google protobuf, facebook of thrift, apache's avro, for they had a source-level package. To support the http protocol, but also encapsulates fastjson and has interface calls them.
     In the transport layer provides custom protocol format, through a unified transport.
     
     /**
 * /**
 * </p>The message packet include the message header(use extends) and the message body</p>
 * 
 * </p>the message header 19 bytes,as following : </p> </p>
 * 
 * </p>Offset: 0             2           4       5       6                        15             18</p> 
 * </p>      : +-------------+-----------+-------+-------+-------------------------+--------------+</p>
 *             |   magic     |  version  | a|b|c |status |        (long)           |    (int)     |
 * </p>      : +----mn-------+----vn-----+-------+---sc--+-------message id--------+---body size--+</p>
 *             |             |           |       | code  |                         |              |   
 * </p>      : +-------------+-----------+-------+-------+-------------------------+--------------+ 
 * 
 * <pre>
 * |<-                             message header                                                                  ->|
 * +----------------+-----------------+---------------+-------------+--------------+-----------+----------+----------+ 
 * | magic(3 bytes) | version(2 bytes)|serializer type|message type | service type |status code|message id| body size|                                                    |                    |
 * +----------------+-----------------+---------------+-------------+--------------+-----------+----------+----------+
 * |<-                                                                                                             ->|
 * </pre>
 * 
 * 
 * </p>a message packet may has more body chunks, as following:</p> 
 * 
 * </p> Offset: 0        3             (Length + 4)</p> 
 * </p>         +--------+------------------------+</p> 
 * </p> Fields: | Length | Actual message content |</p>
 * </p>         +--------+------------------------+</p>
 * 
 * 
 * The message header, which has the delimited length of 12.
 * More detail, please @see {@link Packet}
 * 
 * @author <a href="mailto:sunchao6106@163.com">sunchao</a>
 *
 */
