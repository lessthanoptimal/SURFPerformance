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

import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.benchmark.homography.CreateDetectDescribeFile;
import boofcv.struct.feature.SurfFeature;
import boofcv.struct.image.ImageFloat32;

import java.io.FileNotFoundException;

/**
 * @author Peter Abeles
 */
public class CreateDetectDescribeFiles {
	public static void doStuff( String directory , String imageSuffix ) throws FileNotFoundException {

		DetectDescribePoint<ImageFloat32,SurfFeature> alg =
				FactorySift.detectDescribe();

		CreateDetectDescribeFile<ImageFloat32,SurfFeature> cdf =
				new CreateDetectDescribeFile<ImageFloat32,SurfFeature>(alg,ImageFloat32.class,"BOOFCV_SIFTN");

		cdf.directory(directory,imageSuffix);
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
