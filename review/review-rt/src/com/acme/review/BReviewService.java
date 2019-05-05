package com.acme.review;

import javax.baja.collection.BITable;
import javax.baja.collection.TableCursor;
import javax.baja.file.BFileSystem;
import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.*;
import javax.baja.sys.*;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

@NiagaraProperty(
        name = "roomCount",
        type = "baja:Ord",
        defaultValue = "BOrd.make(\"file:^review/RoomCount.txt\")",
        /*facets = {
                @Facet(name = "\"targetType\"", value = "\"baja:IFile\"")
        }*/
        facets = {
        @Facet( name = "BFacets.TARGET_TYPE", value="\"baja:IFile\"")
        }
)

@NiagaraProperty(
        name = "asyncHandler",
        type = "review:ReviewWorker",
        defaultValue = "BReviewWorker.make()",
        flags = Flags.HIDDEN
)

@NiagaraProperty(
        name = "ObjectCount",
        type = "baja:Integer",
        defaultValue  = "BInteger.make(0)",
        flags = Flags.SUMMARY | Flags.READONLY
)

@NiagaraAction(
        name = "updateCount",
        flags = Flags.ASYNC
)

@NiagaraType

public class BReviewService extends BAbstractService {
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.acme.review.BReviewService(2046872596)1.0$ @*/
/* Generated Fri May 03 09:32:55 EDT 2019 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "roomCount"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code roomCount} property.
   * @see #getRoomCount
   * @see #setRoomCount
   */
  public static final Property roomCount = newProperty(0, BOrd.make("file:^review/RoomCount.txt"), BFacets.make(BFacets.TARGET_TYPE, "baja:IFile"));
  
  /**
   * Get the {@code roomCount} property.
   * @see #roomCount
   */
  public BOrd getRoomCount() { return (BOrd)get(roomCount); }
  
  /**
   * Set the {@code roomCount} property.
   * @see #roomCount
   */
  public void setRoomCount(BOrd v) { set(roomCount, v, null); }

////////////////////////////////////////////////////////////////
// Property "asyncHandler"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code asyncHandler} property.
   * @see #getAsyncHandler
   * @see #setAsyncHandler
   */
  public static final Property asyncHandler = newProperty(Flags.HIDDEN, BReviewWorker.make(), null);
  
  /**
   * Get the {@code asyncHandler} property.
   * @see #asyncHandler
   */
  public BReviewWorker getAsyncHandler() { return (BReviewWorker)get(asyncHandler); }
  
  /**
   * Set the {@code asyncHandler} property.
   * @see #asyncHandler
   */
  public void setAsyncHandler(BReviewWorker v) { set(asyncHandler, v, null); }

////////////////////////////////////////////////////////////////
// Property "ObjectCount"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code ObjectCount} property.
   * @see #getObjectCount
   * @see #setObjectCount
   */
  public static final Property ObjectCount = newProperty(Flags.SUMMARY | Flags.READONLY, ((BInteger)(BInteger.make(0))).getInt(), null);
  
  /**
   * Get the {@code ObjectCount} property.
   * @see #ObjectCount
   */
  public int getObjectCount() { return getInt(ObjectCount); }
  
  /**
   * Set the {@code ObjectCount} property.
   * @see #ObjectCount
   */
  public void setObjectCount(int v) { setInt(ObjectCount, v, null); }

////////////////////////////////////////////////////////////////
// Action "updateCount"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code updateCount} action.
   * @see #updateCount()
   */
  public static final Action updateCount = newAction(Flags.ASYNC, null);
  
  /**
   * Invoke the {@code updateCount} action.
   * @see #updateCount
   */
  public void updateCount() { invoke(updateCount, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReviewService.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

//SERVICE CALLBACKS................//
        @Override
        public void serviceStarted(){
                BOrd fileOrd = getRoomCount();
                try{
                        BIFile file = (BIFile)fileOrd.get(this);
                        file.write("Log start: ".concat(
                                BAbsTime.now().encodeToString()).getBytes());
                }catch(UnresolvedException ue){
                        logger.warning("File ORD Unresolved: \n"+
                                ue.getCause());
                        OrdQuery[] qps = fileOrd.parse();
                        FilePath fp = (FilePath)qps[qps.length-1];
                        try{
                                BIFile file = BFileSystem.INSTANCE.makeFile(fp);
                                file.write("Log start: ".concat(
                                        BAbsTime.now().encodeToString()).getBytes());
                        }
                        catch(IOException ioe){ logger.severe("File IO Error - Couldn't make the file: \n"+
                                ioe.getCause());}

                }catch(IOException io){
                        logger.severe("File IO Error: "+io.getCause());
                }
        }

//SLOT CALLBACKS...................//

//ACTION CALLBACKS.................//

        public void doUpdateCount(){
                BOrd query = BOrd.make("station:|slot:/|bql:select name from review:Rectangle");
                try{
                        BITable t = (BITable)query.get(this.getParentComponent());
                        TableCursor c = t.cursor();
                        while(c.next()){
                                setObjectCount(getObjectCount()+1);
                        }
                        try{
                                BIFile fileObj = (BIFile)getRoomCount().get(this);
                                FilePath fp = fileObj.getFilePath();
                                File f = BFileSystem.INSTANCE.pathToLocalFile(fp);

                                FileWriter fw = new FileWriter(f, true);
                                fw.write("Room Count Computed: "+
                                        BAbsTime.now().encodeToString()+
                                        " : "+getObjectCount());
                                fw.close();
                        }catch(UnresolvedException uo){
                                logger.severe("FILE IO - ERROR - Unresolved file in doUpdate Count\n"+
                                        uo.getCause());
                        }catch(IOException io){
                                logger.severe("FILE IO - ERROR - in doUpdate Count\n"+
                                        io.getCause());}
                }catch(Exception e){
                        logger.info("\n".concat(e.getCause()+"\n"));
                        System.out.println(e.getStackTrace());
                }
        }

        //async handler callback
        public IFuture post(Action a, BValue arg, Context c){
                Invocation work = new Invocation(this, a, arg, c);
                getAsyncHandler().postWork(work);
                return null;
        }

//PARENT CLASS METHOD OVERRIDES....//
        @Override
        public Type[] getServiceTypes(){ return serviceTypes; }

//PRIVATE FIELDS
        private static Type[] serviceTypes = new Type[]{ TYPE };
        private static Logger logger = Logger.getLogger("Smolinski_Victor_review");
}
