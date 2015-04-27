package utils.spock;

import android.support.test.InstrumentationRegistry;

import org.spockframework.runtime.extension.AbstractMethodInterceptor;
import org.spockframework.runtime.extension.IMethodInvocation;
import org.spockframework.runtime.model.FieldInfo;

public class WithTestContextInterceptor extends AbstractMethodInterceptor {
    private final FieldInfo fieldInfo;

    public WithTestContextInterceptor(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    @Override
    public void interceptSetupMethod(IMethodInvocation invocation) throws Throwable {
        fieldInfo.writeValue(invocation.getInstance(), InstrumentationRegistry.getContext());
        invocation.proceed();
    }
}