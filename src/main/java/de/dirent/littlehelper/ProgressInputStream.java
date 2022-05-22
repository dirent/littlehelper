package de.dirent.littlehelper;


import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ProgressInputStream extends FilterInputStream {

    private long max = 0L;
    private long read = 0L;

    private double percent = 0;
    private double step = 10.0;

    public ProgressInputStream( InputStream is, long total ) {
        super(is);
        max = total;
        if( max > 1000000000000L ) {
        	// 1TB
        	step = 0.01;
        } else if( max > 100000000000L ) {
        	// 100GB
        	step = 0.1;
        } else if( max > 10000000000L ) {
        	// 10GB
        	step = 1;
        } else if( max > 1000000000L ) {
        	// 1GB
        	step = 5.0;
        } else {
        	step = 10.0;
        }
        progress();
    }

    protected void progress() {
        double current = (max>0.0) ? ( (double) read / (double) max * 100.0) : 0;
        if( current >= percent+step ) {
            while( current >= percent+step ) {
                percent += step;
            }
            System.out.println( percent+"% of " + max + " total." );
        }
    }

    public int read() throws IOException {
        int t = super.read();
        read++;
        return t;
    }

    public int read(byte[] data) throws IOException {
        int t = super.read(data);
        if ( t > 0 ) {
            read += t;
            progress();
        }
        return t;
    }

    public int read(byte[] data, int offset, int length) throws IOException {
        int t = super.read(data, offset, length);
        if ( t > 0 ) {
            read += t;
            progress();
        }
        return t;
    }

    public long skip(long length) throws IOException {
        long t = super.skip(length);
        read += t;
        progress();
        return t;
    }
}
