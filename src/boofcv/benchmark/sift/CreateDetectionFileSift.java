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

import boofcv.abst.feature.detect.interest.InterestPointDetector;
import boofcv.benchmark.surf.homography.CreateDetectionFile;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageSingleBand;

import java.io.FileNotFoundException;

/**
 * @author Peter Abeles
 */
public class CreateDetectionFileSift {
	public static void doStuff( String directory , String suffix ) throws FileNotFoundException {
		// below are the settings used for detect stability test
		// graf image 1 with 2000 features
		InterestPointDetector<ImageFloat32> alg = FactoryInterestPoint.siftDetector(1.6, 5, 4, false, 3, 5, -1);
		// below is the settings used for describe stability test
//		InterestPointDetector<T> alg = FactoryInterestPoint.fastHessian(3, 1, -1, 1, 9, 4, 4);

		CreateDetectionFile<ImageFloat32> cdf =
				new CreateDetectionFile<ImageFloat32>(alg,null,ImageFloat32.class,"BSIFT");
		cdf.directory(directory,suffix);
	}

	public static void main( String args[] ) throws FileNotFoundException {
		doStuff("data/bikes/",".png");
		doStuff("data/boat/",".png");
		doStuff("data/graf/",".png");
		doStuff("data/leuven/",".png");
		doStuff("data/ubc/",".png");
		doStuff("data/trees/",".png");
		doStuff("data/wall/",".png");
		doStuff("data/bark/",".png");
	}
}
