package com.drunkencod.create_nerfed_drills.platform;

import java.util.ServiceLoader;

import com.drunkencod.create_nerfed_drills.Constants;
import com.drunkencod.create_nerfed_drills.config.IConfigHelper;
import com.drunkencod.create_nerfed_drills.platform.services.IPlatformHelper;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IConfigHelper CONFIG = load(IConfigHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
