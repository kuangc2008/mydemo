package com.example.demo.feb.bitmapfun.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * A cache that uses a bounded amount of space on a filesystem. Each cache
 * entry has a string key and a fixed number of values. Values are byte
 * sequences, accessiable as streams or files.  Each value must be between  
 * 0 and Integer.Max_value bytes in length.
 * 
 * <p>The cache stores its data in a dictory on the filesiystem. This 
 * directory must be exclusive to the cache; the cache my delete or overwrite
 * files from its directory. it is an error for multiple processes to use the 
 * same cache directory at the same time.
 * 
 * <p>this cache limits the number of bytes that it will sotre on the filesystem.
 * when the number of stored bytes exceeds the limit, the cache will remove
 * entries in the background until the limit is satisfied. the limit is not strict:
 * the cache my temporarily exceed it while waiting for files to be deleted. 
 * the limit does not include filesystem overhead or the cache jorunal 
 * so the space-sensitive applications should set a conservative limit.
 * 
 * <p>Clinets call edit to create or update the values of an entry. An entry may
 *  have only one editor at one time; if a value is not avaiable to be edited then
 *  edit will return null.
 *  <ul>
 *      <li> when an entry is being <strong>created</strong>. it is necessary to 
 *          supply a full set of values;  the empty value should be used as a
 *          placeholder if necessary.
 *      <li> when an entry is being <strong>edited</strong>, it is not necessary
 *          to supply data for every value; values default to their previous value.
 *  </ul>
 *  Every edit call must be matched by a call to editor.commit or editor.abort. 
 *  Committing is atomic: a read observes the full set of value as they were before
 *  or after the commit, but never a mix of values.
 *  
 *  <p>Clients call get to read a snapshot of an entry. the read will observe the 
 *  value of the time that get was called. updates and removals after the call do
 *  not impact ongoing reads.
 *  
 *  <p> this class is tolerant of some I/O errors.  
 *
 */
public class DiskLruCache implements Closeable{

    @Override
    public void close() throws IOException {
        
    }

}
