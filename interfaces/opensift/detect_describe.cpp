#include "cv.h"
#include "opencv2/core/core.hpp"
extern "C" {
  #include "sift.h"
  #include "imgfeatures.h"
}
#include <highgui.h>
#include <ctime>
#include <iostream>

#include <vector>

using namespace std;

std::vector<string> imageNames;

void process( IplImage *image , FILE *output)
{
  // NOTE:  Need to comment out orientation and description lines from detection code
  //        or else the analysis will be messed up
  struct feature* feat;
//    int N = _sift_features( ipl, &feat, SIFT_INTVLS, SIFT_SIGMA, SIFT_CONTR_THR,
//         SIFT_CURV_THR, SIFT_IMG_DBL, SIFT_DESCR_WIDTH,
//         SIFT_DESCR_HIST_BINS );
  int N = _sift_features( image, &feat, SIFT_INTVLS, SIFT_SIGMA, SIFT_CONTR_THR,
       SIFT_CURV_THR, 0, SIFT_DESCR_WIDTH,
       SIFT_DESCR_HIST_BINS );

  fprintf(output,"%d\n",FEATURE_MAX_D);
  // save detected points and description to a file
  for( int i = 0; i < N; i++ ) {
      struct feature &p = feat[i];
      fprintf(output,"%7.3f %7.3f %7.5f",p.x,p.y,p.ori);
      for( int j = 0; j < FEATURE_MAX_D; j++ ) {
        fprintf(output," %0.10f",p.descr[j]);
      }
      fprintf(output,"\n");
  }
  fclose(output);

  free(feat);

  printf("Done: %d\n",N);
}

int main( int argc , char **argv )
{
    if( argc < 2 )
        throw std::runtime_error("[directory] [detected suffix]");

    char *nameDirectory = argv[1];

    char filename[1024];

    printf("directory name: %s\n",nameDirectory);

    for( int i = 1; i <= 6; i++ ) {
        sprintf(filename,"%s/img%d.png",nameDirectory,i);
        IplImage *img=cvLoadImage(filename);
        if( img == NULL ) {
            fprintf(stderr,"Couldn't open image file: %s\n",filename);
            throw std::runtime_error("Failed to open");
        }

        sprintf(filename,"%s/DESCRIBE_img%d_%s.txt",nameDirectory,i,"OpenSIFT");
        FILE *output = fopen(filename,"w");
        if( output == NULL ) {
            fprintf(stderr,"Couldn't open file: %s\n",filename);
            throw std::runtime_error("Failed to open");
        }

        printf("Processing %s\n",filename);
        process(img,output);
    }

}
