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
import boofcv.benchmark.homography.CreateDescriptionFile;
import boofcv.struct.feature.SurfFeature;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageSingleBand;

import java.io.FileNotFoundException;

/**
 * @author Peter Abeles
 */
public class CreateDescriptionFileSurf {
	public static <T extends ImageSingleBand>
	void doStuff( String directory , String imageSuffix , Class<T> imageType ) throws FileNotFoundException {
		DescribeRegionPoint<T,SurfFeature> alg;
		CreateDescriptionFile<T,SurfFeature> cdf;

		alg = FactorySurf.surf(true, imageType);
		cdf = new CreateDescriptionFile<T,SurfFeature>(alg,imageType,"BoofCV_MSURF");
		cdf.directory(directory,imageSuffix,"SURF.txt");

		alg = FactorySurf.surf(false, imageType);
		cdf = new CreateDescriptionFile<T,SurfFeature>(alg,imageType,"BoofCV_SURF");
		cdf.directory(directory,imageSuffix,"SURF.txt");
	}

	public static void main( String args[] ) throws FileNotFoundException {
		Class type = ImageFloat32.class;

		doStuff("data/bikes/",".png",type);
		doStuff("data/boat/",".png",type);
		doStuff("data/graf/",".png",type);
		doStuff("data/leuven/",".png",type);
		doStuff("data/ubc/",".png",type);
		doStuff("data/trees/",".png",type);
		doStuff("data/wall/",".png",type);
		doStuff("data/bark/",".png",type);
	}
}
