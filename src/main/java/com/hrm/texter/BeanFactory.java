package com.hrm.texter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

public class BeanFactory {
    private Map<Class<?>, Object> context;

    private static class Holder {
        public static final BeanFactory INSTANCE = new BeanFactory();
    }

    public static BeanFactory getInstance() {
        return Holder.INSTANCE;
    }

    private BeanFactory() {
        context = new HashMap<>();
        context.put(Random.class, new Random());
        context.put(ResourceReader.class, new ResourceReaderImpl());
        context.put(Repository.class, new RepositoryImpl(
                (ResourceReader) context.get(ResourceReader.class),
                (Random) context.get(Random.class)));
        context.put(Writer.class, new WriterImpl());
        context.put(Encryptor.class, new EncryptorImpl());
        context.put(GeneratorService.class, new GeneratorServiceImpl(
                (Repository) context.get(Repository.class),
                (Encryptor) context.get(Encryptor.class), 
                (Random) context.get(Random.class)));
        context.put(Application.class, new Application(
                (GeneratorService) context.get(GeneratorService.class),
                (Writer) context.get(Writer.class), 
                (Repository) context.get(Repository.class)));
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) context.get(clazz);
    }

}
