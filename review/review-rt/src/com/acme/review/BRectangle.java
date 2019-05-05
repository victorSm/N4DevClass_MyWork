package com.acme.review;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.util.logging.Logger;

@NiagaraType

@NiagaraProperty(
        name = "length",
        type = "baja:Double",
        defaultValue = "BDouble.make(0.0)",
        flags = Flags.SUMMARY
)

@NiagaraProperty(
        name = "width",
        type = "baja:Double",
        defaultValue = "BDouble.make(0.0)",
        flags = Flags.SUMMARY
)

@NiagaraProperty(
        name = "threshold",
        type = "baja:Double",
        defaultValue = "BDouble.make(4073.26)",
        flags = Flags.SUMMARY
)

@NiagaraProperty(
        name = "area",
        type = "baja:Double",
        defaultValue = "BDouble.make(0.0)",
        flags = Flags.SUMMARY | Flags.READONLY
)

@NiagaraTopic(
        name = "detected",
        eventType = "BDouble",
        flags = Flags.SUMMARY
)

public class BRectangle extends BComponent {
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.acme.review.BRectangle(1002146659)1.0$ @*/
/* Generated Thu May 02 09:33:01 EDT 2019 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "length"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code length} property.
   * @see #getLength
   * @see #setLength
   */
  public static final Property length = newProperty(Flags.SUMMARY, ((BDouble)(BDouble.make(0.0))).getDouble(), null);
  
  /**
   * Get the {@code length} property.
   * @see #length
   */
  public double getLength() { return getDouble(length); }
  
  /**
   * Set the {@code length} property.
   * @see #length
   */
  public void setLength(double v) { setDouble(length, v, null); }

////////////////////////////////////////////////////////////////
// Property "width"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code width} property.
   * @see #getWidth
   * @see #setWidth
   */
  public static final Property width = newProperty(Flags.SUMMARY, ((BDouble)(BDouble.make(0.0))).getDouble(), null);
  
  /**
   * Get the {@code width} property.
   * @see #width
   */
  public double getWidth() { return getDouble(width); }
  
  /**
   * Set the {@code width} property.
   * @see #width
   */
  public void setWidth(double v) { setDouble(width, v, null); }

////////////////////////////////////////////////////////////////
// Property "threshold"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code threshold} property.
   * @see #getThreshold
   * @see #setThreshold
   */
  public static final Property threshold = newProperty(Flags.SUMMARY, ((BDouble)(BDouble.make(4073.26))).getDouble(), null);
  
  /**
   * Get the {@code threshold} property.
   * @see #threshold
   */
  public double getThreshold() { return getDouble(threshold); }
  
  /**
   * Set the {@code threshold} property.
   * @see #threshold
   */
  public void setThreshold(double v) { setDouble(threshold, v, null); }

////////////////////////////////////////////////////////////////
// Property "area"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code area} property.
   * @see #getArea
   * @see #setArea
   */
  public static final Property area = newProperty(Flags.SUMMARY | Flags.READONLY, ((BDouble)(BDouble.make(0.0))).getDouble(), null);
  
  /**
   * Get the {@code area} property.
   * @see #area
   */
  public double getArea() { return getDouble(area); }
  
  /**
   * Set the {@code area} property.
   * @see #area
   */
  public void setArea(double v) { setDouble(area, v, null); }

////////////////////////////////////////////////////////////////
// Topic "detected"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code detected} topic.
   * @see #fireDetected
   */
  public static final Topic detected = newTopic(Flags.SUMMARY, null);
  
  /**
   * Fire an event for the {@code detected} topic.
   * @see #detected
   */
  public void fireDetected(BDouble event) { fire(detected, event, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRectangle.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private static Logger logger = Logger.getLogger("Smolinski_Victor_review");

    //********PROPERTIES CALLBACKS*******//

    @Override
    public void changed(Property prop, Context cx){

        //calculate area if the width or length change...
        if(prop.getName().equals("length") ||
                prop.getName().equals("width")){
            setArea(getWidth() * getLength());
        }

        //check our topic if any property changes, fire topic as per instructions...
        if(getArea() > getThreshold()){
          fireDetected(BDouble.make(getArea()));
          logger.info("\nreview: Rectangle\n"+
                      "\tTime: "+BAbsTime.now()+"\n"+
                      "\tLength: "+getLength()+"\tWidth: "+getWidth()+"\tArea: "+getArea());
        }
    }

    //METHODS

  @Override
  public BIcon getIcon() {
//      return BIcon.std("iconname.png");
      return BIcon.make("module://review/com/acme/review/icons/boxIcon.png");
    }

}
