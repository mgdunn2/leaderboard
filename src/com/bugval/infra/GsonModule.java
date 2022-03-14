package com.bugval.infra;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import javax.inject.Singleton;
import java.util.ServiceLoader;

@Module
public class GsonModule {
  /**
   * Use the ServiceLoader to load all Type Adapters created using the Immutable Gson TypeAdapters.
   * {@link Gson} is stateless and threadsafe so only create a single instance for the lifetime of
   * the application
   */
  @Provides
  @Singleton
  public Gson commonBuilder() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
      gsonBuilder.registerTypeAdapterFactory(factory);
    }
    return gsonBuilder.create();
  }
}
