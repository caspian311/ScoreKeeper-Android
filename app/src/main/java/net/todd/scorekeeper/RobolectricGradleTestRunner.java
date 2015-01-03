package net.todd.scorekeeper;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.res.Fs;

public class RobolectricGradleTestRunner extends RobolectricTestRunner {
    public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(org.robolectric.annotation.Config config) {
        return super.getAppManifest(config);
    }

    //    @Override
//    protected AndroidManifest getAppManifest(Config config) {
//        String manifestProperty = System.getProperty("android.manifest");
//        String resProperty = System.getProperty("android.resources");
//        String assetsProperty = System.getProperty("android.assets");
//
//        return new AndroidManifest(Fs.fileFromPath(manifestProperty), Fs.fileFromPath(resProperty),
//                Fs.fileFromPath(assetsProperty));
//    }
}