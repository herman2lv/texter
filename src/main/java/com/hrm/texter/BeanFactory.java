package com.hrm.texter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import com.hrm.texter.data.Repository;
import com.hrm.texter.data.ResourceReader;
import com.hrm.texter.data.impl.RepositoryImpl;
import com.hrm.texter.data.impl.ResourceReaderImpl;
import com.hrm.texter.out.Writer;
import com.hrm.texter.out.impl.WriterImpl;
import com.hrm.texter.service.Encryptor;
import com.hrm.texter.service.GeneratorService;
import com.hrm.texter.service.impl.EncryptorImpl;
import com.hrm.texter.service.impl.GeneratorServiceImpl;

public enum BeanFactory {
    INSTANCE;
    
    private final Map<Class<?>, Object> context = new HashMap<>();

    public Random random() {
        return get(Random.class, Random::new);
    }

    public ResourceReader reader() {
        return get(ResourceReader.class, ResourceReaderImpl::new);
    }

    public Repository repository() {
        return get(Repository.class, () -> new RepositoryImpl(reader(), random()));
    }

    public Writer writer() {
        return get(Writer.class, WriterImpl::new);
    }

    public Encryptor encryptor() {
        return get(Encryptor.class, EncryptorImpl::new);
    }

    public GeneratorService generatorService() {
        return get(GeneratorService.class, () -> new GeneratorServiceImpl(repository(), encryptor(), random()));
    }

    public Application application() {
        return get(Application.class, () -> new Application(generatorService(), writer(), repository()));
    }

    @SuppressWarnings("unchecked")
    private <T> T get(Class<T> clazz, Supplier<T> ifAbsent) {
        Object instance = context.get(clazz);
        if (instance == null) {
            instance = ifAbsent.get();
        }
        return (T) instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) context.get(clazz);
    }

}
