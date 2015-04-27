package utils.spock;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FieldInfo;

public class WithTestContextExtension extends AbstractAnnotationDrivenExtension<WithTestContext> {
    @Override public void visitFieldAnnotation(WithTestContext annotation, FieldInfo field) {
        WithTestContextInterceptor interceptor = new WithTestContextInterceptor(field);
        field.getParent().addSetupInterceptor(interceptor);
    }
}
