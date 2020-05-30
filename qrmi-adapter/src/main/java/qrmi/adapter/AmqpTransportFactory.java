package qrmi.adapter;

import java.util.function.Consumer;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;

public class AmqpTransportFactory implements BeanClassLoaderAware, InitializingBean, FactoryBean<Object> {
    private static final Logger logger = Logger.getLogger(AmqpTransportFactory.class);

    public static final class Builder {
        private final AmqpTransportFactory subject = new AmqpTransportFactory();

        public Builder with(Consumer<AmqpTransportFactory> configure) {
            if(configure != null) {
                configure.accept(subject);
            }
            return this;
        }

        public AmqpTransportFactory build() {
            subject.afterPropertiesSet();
            return subject;
        }
    }

    /**
     * Obtain the builder for this factory for programmatic instantiation.
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    private Object service;
    private Class<?> serviceInterface;
    private Object proxy;
    private AmqpTransport transport;

    /**
     * The AMQP transport implementation.
     *
     * @return
     */
    public AmqpTransport getTransport() {
        return transport;
    }

    /**
     * The AMQP transport implementation.
     *
     * @param transport
     */
    public void setTransport(AmqpTransport transport) {
        this.transport = transport;
    }

    /**
     * The underlying (legacy) service implementation.
     *
     * @return
     */
    public Object getService() {
        return service;
    }

    /**
     * The underlying (legacy) service implementation.
     *
     * @param service
     */
    public void setService(Object service) {
        this.service = service;
    }

    /**
     * The service (adapter) interface, i.e. {@link AmqpProducer}.
     *
     * @return
     */
    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    /**
     * The service (adapter) interface, i.e. {@link AmqpProducer}.
     *
     * @param serviceInterface
     */
    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    protected Object getProxy() {
        return this.proxy;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public Object getObject() {
        return getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    @Override
    public void afterPropertiesSet() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addInterface(getServiceInterface());
        proxyFactory.setTarget(getService());
        proxyFactory.setOpaque(true);

        /*
         * Before each method invocation, a thread local will be set using the provided AMQP transport.
         */

        logger.trace(String.format("AMQP transport available: %s", getTransport()));
        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
            AmqpTransportContext.set(getTransport());
            return invocation.proceed();
        });

        this.proxy = proxyFactory.getProxy(beanClassLoader);
    }
}
