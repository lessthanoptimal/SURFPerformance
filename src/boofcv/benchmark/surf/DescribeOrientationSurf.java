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
import boofcv.abst.feature.orientation.OrientationIntegral;
import boofcv.alg.feature.describe.DescribePointSurf;
import boofcv.alg.transform.ii.GIntegralImageOps;
import boofcv.struct.feature.SurfFeature;
import boofcv.struct.image.ImageSingleBand;

/**
 * Adds orientation estimation to SURF description calculation.
 *
 * @author Peter Abeles
 */
public class DescribeOrientationSurf<T extends ImageSingleBand, II extends ImageSingleBand>
		implements DescribeRegionPoint<T,SurfFeature>
{
	private OrientationIntegral<II> orientation;
	private DescribePointSurf<II> describe;

	// storage for integral image
	private II ii;

	public DescribeOrientationSurf(OrientationIntegral<II> orientation,
								   DescribePointSurf<II> describe) {
		this.orientation = orientation;
		this.describe = describe;
	}

	@Override
	public void setImage(T image) {
		if( ii != null ) {
			ii.reshape(image.width,image.height);
		}

		// compute integral image
		ii = GIntegralImageOps.transform(image, ii);
		orientation.setImage(ii);
		describe.setImage(ii);
	}

	@Override
	public SurfFeature createDescription() {
		return describe.createDescription();
	}

	@Override
	public int getDescriptionLength() {
		return describe.getDescriptionLength();
	}

	@Override
	public boolean isInBounds(double x, double y, double orientation, double scale) {
		return true;
	}

	@Override
	public SurfFeature process(double x, double y, double angle, double scale, SurfFeature ret) {
		if( ret == null )
			ret = createDescription();

		orientation.setScale(scale);
		angle = orientation.compute(x,y);
		describe.describe(x,y, angle, scale, ret);

		return ret;
	}

	@Override
	public boolean requiresScale() {
		return false;
	}

	@Override
	public boolean requiresOrientation() {
		return true;
	}

	@Override
	public Class<SurfFeature> getDescriptionType() {
		return SurfFeature.class;
	}
}
