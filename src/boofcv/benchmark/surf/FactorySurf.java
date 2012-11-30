/*
 * Copyright (c) 2011-2012, Peter Abeles. All Rights Reserved.
 *
 * This file is part of the SURF Performance Benchmark
 * (https://github.com/lessthanoptimal/SURFPerformance).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.benchmark.surf;

import boofcv.abst.feature.describe.DescribeRegionPoint;
import boofcv.abst.feature.describe.WrapDescribeSurf;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.detdesc.WrapDetectDescribeSurf;
import boofcv.abst.feature.detect.extract.FeatureExtractor;
import boofcv.abst.feature.orientation.OrientationIntegral;
import boofcv.alg.feature.describe.DescribePointSurf;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
import boofcv.alg.transform.ii.GIntegralImageOps;
import boofcv.factory.feature.describe.FactoryDescribePointAlgs;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.orientation.FactoryOrientation;
import boofcv.factory.feature.orientation.FactoryOrientationAlgs;
import boofcv.struct.feature.SurfFeature;
import boofcv.struct.image.ImageSingleBand;

/**
 * @author Peter Abeles
 */
public class FactorySurf {
	/**
	 * Java port of Pan-o-Matic's descriptor to make examing its behavior easier.
	 */
	public static <T extends ImageSingleBand, II extends ImageSingleBand>
	DescribeRegionPoint<T,SurfFeature> surfPanOMaticInBoofCV(boolean isOriented, Class<T> imageType) {
		OrientationIntegral<II> orientation = null;

		Class<II> integralType = GIntegralImageOps.getIntegralType(imageType);

		if( isOriented )
			orientation = FactoryOrientationAlgs.sliding_ii(0.65, Math.PI / 3.0, 8, -1, 6, integralType);

		DescribePointSurf<II> alg = new DescribePointSurfPanOMatic<II>(integralType);
		return new WrapDescribeSurf<T,II>( alg );
	}

	/**
	 * Creates a BoofCV SURF descriptor
	 */
	public static <T extends ImageSingleBand, II extends ImageSingleBand>
	DescribeRegionPoint<T,SurfFeature> surf( boolean modified , Class<T> imageType )
	{
		Class integralType = GIntegralImageOps.getIntegralType(imageType);

		DescribePointSurf<II> describe;
		OrientationIntegral<II> orientation =
				FactoryOrientation.surfDefault(modified, integralType);

		if( modified ) {
			describe = FactoryDescribePointAlgs.msurf(integralType);
		} else {
			describe = FactoryDescribePointAlgs.surf(integralType);
		}

		return new DescribeOrientationSurf<T,II>(orientation,describe);
	}

	public static <T extends ImageSingleBand, II extends ImageSingleBand>
	DetectDescribePoint<T,SurfFeature> detectDescribe( boolean modified , Class<T> imageType) {
		Class integralType = GIntegralImageOps.getIntegralType(imageType);

		DescribePointSurf<II> describe;
		OrientationIntegral<II> orientation =
				FactoryOrientation.surfDefault(modified, integralType);

		if( modified ) {
			describe = FactoryDescribePointAlgs.msurf(integralType);
		} else {
			describe = FactoryDescribePointAlgs.surf(integralType);
		}

		FeatureExtractor extractor = FactoryFeatureExtractor.nonmax(1, 80, 5, true);
		FastHessianFeatureDetector<II> detector = new FastHessianFeatureDetector<II>(extractor, -1, 1, 9, 4, 4);

		return new WrapDetectDescribeSurf<T,II>(detector, orientation,describe);
	}
}
