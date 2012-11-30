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

package boofcv.benchmark.sift;

import boofcv.abst.feature.describe.DescribeRegionPoint;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.alg.feature.describe.DescribePointSift;
import boofcv.alg.feature.detect.interest.SiftImageScaleSpace;
import boofcv.alg.feature.orientation.OrientationHistogramSift;
import boofcv.factory.feature.describe.FactoryDescribePointAlgs;
import boofcv.factory.feature.detdesc.FactoryDetectDescribe;
import boofcv.struct.feature.SurfFeature;
import boofcv.struct.image.ImageFloat32;

/**
 * @author Peter Abeles
 */
public class FactorySift {

	public static DescribeRegionPoint<ImageFloat32,SurfFeature>
	createDescriptor() {
		SiftImageScaleSpace ss = new SiftImageScaleSpace(1.6f, 5, 4, false);
		OrientationHistogramSift orientation = new OrientationHistogramSift(32,2.5,1.5);
		DescribePointSift sift = FactoryDescribePointAlgs.sift(4, 8, 8);

		return new DescribeOrientationSift(orientation,sift,ss);
	}

	public static DetectDescribePoint<ImageFloat32,SurfFeature>
	detectDescribe() {
		return FactoryDetectDescribe.sift(1.6,5,4,false,3, 5, -1 , 10,32);
	}
}
