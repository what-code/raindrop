package com.b5m.raindrop.cache.memcache;

import com.b5m.raindrop.cache.memcache.keyprovider.PrefixKeyProvider;

import net.rubyeye.xmemcached.CommandFactory;
import net.rubyeye.xmemcached.HashAlgorithm;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.MemcachedSessionLocator;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.command.TextCommandFactory;
import net.rubyeye.xmemcached.impl.ArrayMemcachedSessionLocator;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.impl.PHPMemcacheSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.transcoders.TokyoTyrantTranscoder;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.transcoders.WhalinTranscoder;
import net.rubyeye.xmemcached.transcoders.WhalinV1Transcoder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class XMemcachedClientFactory implements MemcachedClientFactory {
	
	public static final String PROP_SERVERS = Config.PROP_PREFIX + "servers";
	public static final String PROP_READ_BUFFER_SIZE = Config.PROP_PREFIX
			+ "readBufferSize";
	public static final String PROP_OPERATION_TIMEOUT = Config.PROP_PREFIX
			+ "operationTimeout";
	public static final String PROP_HASH_ALGORITHM = Config.PROP_PREFIX
			+ "hashAlgorithm";
	public static final String PROP_COMMAND_FACTORY = Config.PROP_PREFIX
			+ "commandFactory";

	public static final String PROP_SESSION_LOCATOR = Config.PROP_PREFIX
			+ "sessionLocator";
	
	public static final String PROP_CONNECTION_POOL_SIZE = Config.PROP_PREFIX
            + "connectionPoolSize";
	
	public static final String PROP_CONNECT_TIMEOUT = Config.PROP_PREFIX
            + "connectTimeout";
	
	
	public static final String PROP_TRANSCODER = Config.PROP_PREFIX + "transcoder";
	public static final String PROP_COMPRESSION_THRESHOLD = Config.PROP_PREFIX + "compressionThreshold";
	public static final int DEFAULT_COMPRESSION_THRESHOLD = 16 * 1024;

	private PropertiesHelper properties;
	
	public XMemcachedClientFactory(PropertiesHelper properties) {
		this.properties = properties;
	}
	
	@Override
	public MemcachedClientWrapper createMemcachedClient(String model) throws Exception {
		// TODO Auto-generated method stub
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil
				.getAddresses(getServerList()));
		builder.setCommandFactory(getCommandFactory());
		builder.setSessionLocator(getSessionLocator());
		builder.setTranscoder(getTranscoder());
		builder.getConfiguration()
				.setSessionReadBufferSize(getReadBufferSize());
		builder.setConnectionPoolSize(getConnectionPoolSize());
		builder.setConnectTimeout(getConnectTimeoutMillis());
		//builder.getConfiguration().setReadThreadCount(4);
		MemcachedClient client = builder.build();
		
		client.setOpTimeout(getOperationTimeoutMillis());
		// TODO 考虑改成配置
		client.setKeyProvider(new PrefixKeyProvider(model));
		return new MemcachedClientWrapper(client);
	}
	
	@SuppressWarnings("rawtypes")
	protected Transcoder getTranscoder()
	{
		Transcoder transcoder = null;
		if (transcoderNameEquals(SerializingTranscoder.class))
		{
			transcoder = new SerializingTranscoder();
		}
		else if (transcoderNameEquals(TokyoTyrantTranscoder.class))
		{
			transcoder = new TokyoTyrantTranscoder();
		}
		else if (transcoderNameEquals(WhalinTranscoder.class))
		{
			transcoder = new WhalinTranscoder();
		}
		else if (transcoderNameEquals(WhalinV1Transcoder.class))
		{
			transcoder = new WhalinV1Transcoder();
		}
		else
		{
			transcoder = new SerializingTranscoder();
		}
		transcoder.setCompressionThreshold(getCompressionThreshold());
		return transcoder;
	}
	
	protected MemcachedSessionLocator getSessionLocator() {
		if (sessionLocatorNameEquals(ArrayMemcachedSessionLocator.class)) {
			return new ArrayMemcachedSessionLocator(getHashAlgorithm());
		}

		if (sessionLocatorNameEquals(KetamaMemcachedSessionLocator.class)) {
			return new KetamaMemcachedSessionLocator();
			//return new KetamaMemcachedSessionLocator(getHashAlgorithm());
		}

		if (sessionLocatorNameEquals(PHPMemcacheSessionLocator.class)) {
			return new PHPMemcacheSessionLocator(getHashAlgorithm());
		}

		return new ArrayMemcachedSessionLocator();
//		throw new IllegalArgumentException("Unsupported "
//				+ PROP_SESSION_LOCATOR + " value: " + getCommandFactoryName());
	}

	protected CommandFactory getCommandFactory() {
		if (commandFactoryNameEquals(TextCommandFactory.class)) {
			return new TextCommandFactory();
		}

		if (commandFactoryNameEquals(BinaryCommandFactory.class)) {
			return new BinaryCommandFactory();
		}

		return new TextCommandFactory();
		//throw new IllegalArgumentException("Unsupported "
		//		+ PROP_COMMAND_FACTORY + " value: " + getCommandFactoryName());
	}

	private boolean commandFactoryNameEquals(Class<?> cls) {
		return cls.getSimpleName().equals(getCommandFactoryName());
	}
	
	private boolean sessionLocatorNameEquals(Class<?> cls) {
		return cls.getSimpleName().equals(getSessionLocatorName());
	}
	
	private boolean transcoderNameEquals(Class<?> cls) {
		return cls.getSimpleName().equals(getTranscoderName());
	}

	public String getServerList() {
		return this.properties.get(PROP_SERVERS, "localhost:11211");
	}

	public int getReadBufferSize() {
		return this.properties.getInt(PROP_READ_BUFFER_SIZE,
				Config.DEFAULT_SESSION_READ_BUFF_SIZE);
	}
	
    public int getConnectionPoolSize() {
        return this.properties.getInt(PROP_CONNECTION_POOL_SIZE,
        		Config.DEFAULT_CONNECTION_POOL_SIZE);
    }

	public long getOperationTimeoutMillis() {
		return this.properties.getLong(PROP_OPERATION_TIMEOUT,
				Config.DEFAULT_OP_TIMEOUT);
	}

	public long getConnectTimeoutMillis() {
        return this.properties.getLong(PROP_CONNECT_TIMEOUT,
        		Config.DEFAULT_CONNECT_TIMEOUT);
    }
	
	public HashAlgorithm getHashAlgorithm() {
		return this.properties.getEnum(PROP_HASH_ALGORITHM,
				HashAlgorithm.class, HashAlgorithm.NATIVE_HASH);
	}

	public String getCommandFactoryName() {
		return this.properties.get(PROP_COMMAND_FACTORY,
				TextCommandFactory.class.getSimpleName());
	}

	public String getSessionLocatorName() {
		return this.properties.get(PROP_SESSION_LOCATOR,
				ArrayMemcachedSessionLocator.class.getSimpleName());
	}
	
	public String getTranscoderName() {
		return this.properties.get(PROP_TRANSCODER,
				SerializingTranscoder.class.getSimpleName());
	}
	
	public int getCompressionThreshold() {
        return this.properties.getInt(PROP_COMPRESSION_THRESHOLD,
        		DEFAULT_COMPRESSION_THRESHOLD);
    }

	protected PropertiesHelper getProperties() {
		return this.properties;
	}
}
