package com.b5m.raindrop.tao.client;

/**
 * 当调用service的方法的时候，如果发生异常，需要实现异常处理的回调接口
 * @author jacky
 *
 */
public interface IExceptionCallback {

	public void onException(Throwable t);
}
