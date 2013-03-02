package tests;

import static org.junit.Assert.*;

import model.DataPoint;

import org.junit.Test;

/**
 * Test Case to test out the DataPoint class.
 * @author Julian Jocque
 *
 */
public class DataPointTests 
{

	/**
	 * Tests that after default creation both values are null
	 */
	@Test
	public void testDeaultCreateBothNull() 
	{
		DataPoint testPoint = new DataPoint();
		assertEquals(null, testPoint.getX());
		assertEquals(null, testPoint.getY());
	}
	
	/**
	 * Tests that after being created with values those values are correct
	 */
	@Test
	public void testNonDefaultCreate()
	{
		Double value1 = new Double(5.5);
		Double value2 = new Double(4.2);
		DataPoint testPoint = new DataPoint(value1, value2);
		assertEquals(new Double(5.5), testPoint.getX());
		assertEquals(new Double(4.2), testPoint.getY());
	}
	
	/**
	 * Tests that when one value is null and the other isn't that both are correct
	 */
	@Test
	public void testOneNull()
	{
		Double value1 = new Double(4);
		Double value2 = new Double(100);
		DataPoint testPoint1 = new DataPoint(value1, null);
		DataPoint testPoint2 = new DataPoint(null, value2);
		
		assertEquals(new Double(4), testPoint1.getX());
		assertEquals(null, testPoint1.getY());
		assertEquals(null, testPoint2.getX());
		assertEquals(new Double(100), testPoint2.getY());
	}

	/**
	 * Tests comparing when value1 > value2
	 */
	@Test
	public void testCompare1GreaterThan2()
	{
		Double largeNeg = new Double(-9999999999999.999999999999);
		Double negative = new Double(-107.7);
		Double zero = new Double(0.0);
		Double positive = new Double(55.7);
		Double largePos = new Double(99999999999999.999999999999);
		
		DataPoint firstBigNegSecondNeg = new DataPoint(largeNeg, negative);
		DataPoint firstNegSecondBigNeg = new DataPoint(negative, largeNeg);
		DataPoint firstNegSecondZero = new DataPoint(negative, zero);
		DataPoint firstZeroSecondPositive = new DataPoint(zero, positive);
		DataPoint firstPosSecondLargePos = new DataPoint(positive, largePos);
		DataPoint firstLargePosSecondPos = new DataPoint(largePos, positive);
		
		assertEquals(1, firstNegSecondBigNeg.compareTo(firstBigNegSecondNeg));
		assertEquals(1, firstNegSecondZero.compareTo(firstBigNegSecondNeg));
		assertEquals(1, firstZeroSecondPositive.compareTo(firstNegSecondZero));
		assertEquals(1, firstPosSecondLargePos.compareTo(firstZeroSecondPositive));
		assertEquals(1, firstPosSecondLargePos.compareTo(firstNegSecondZero));
		assertEquals(1, firstLargePosSecondPos.compareTo(firstPosSecondLargePos));
	}
	
	/**
	 * Tests comparing when value1 < value2
	 */
	@Test
	public void testCompare2GreaterThan1()
	{
		Double largeNeg = new Double(-9999999999999.999999999999);
		Double negative = new Double(-107.7);
		Double zero = new Double(0.0);
		Double positive = new Double(55.7);
		Double largePos = new Double(99999999999999.999999999999);
		
		DataPoint firstBigNegSecondNeg = new DataPoint(largeNeg, negative);
		DataPoint firstNegSecondBigNeg = new DataPoint(negative, largeNeg);
		DataPoint firstNegSecondZero = new DataPoint(negative, zero);
		DataPoint firstZeroSecondPositive = new DataPoint(zero, positive);
		DataPoint firstPosSecondLargePos = new DataPoint(positive, largePos);
		DataPoint firstLargePosSecondPos = new DataPoint(largePos, positive);
		
		assertEquals(-1, firstBigNegSecondNeg.compareTo(firstNegSecondBigNeg));
		assertEquals(-1, firstBigNegSecondNeg.compareTo(firstNegSecondZero));
		assertEquals(-1, firstNegSecondZero.compareTo(firstZeroSecondPositive));
		assertEquals(-1, firstZeroSecondPositive.compareTo(firstPosSecondLargePos));
		assertEquals(-1, firstNegSecondZero.compareTo(firstPosSecondLargePos));
		assertEquals(-1, firstPosSecondLargePos.compareTo(firstLargePosSecondPos));
	}
	
	/**
	 * Tests comparing when values are equal
	 */
	@Test
	public void testCompareEquals()
	{
		Double negativeValue = new Double(-10);
		Double value1 = new Double(10);
		Double zero = new Double(0);
		DataPoint nullPoint = new DataPoint();
		DataPoint otherNullPoint = new DataPoint();
		DataPoint posPoint = new DataPoint(value1, value1);
		DataPoint otherPosPoint = new DataPoint(value1, value1);
		DataPoint negPoint = new DataPoint(negativeValue, negativeValue);
		DataPoint otherNegPoint = new DataPoint(negativeValue, negativeValue);
		DataPoint zeroPoint = new DataPoint(zero, zero);
		DataPoint otherZeroPoint = new DataPoint(zero, zero);
		DataPoint xNullPoint = new DataPoint(null, zero);
		DataPoint otherXNullPoint = new DataPoint(null, zero);
		DataPoint yNullPoint = new DataPoint(value1, null);
		DataPoint otherYNullPoint = new DataPoint(value1, null);
		
		
		assertEquals(0, posPoint.compareTo(posPoint));
		assertEquals(0, posPoint.compareTo(otherPosPoint));
		assertEquals(0, nullPoint.compareTo(otherNullPoint));
		assertEquals(0, zeroPoint.compareTo(otherZeroPoint));
		assertEquals(0, xNullPoint.compareTo(otherXNullPoint));
		assertEquals(0, yNullPoint.compareTo(otherYNullPoint));
		assertEquals(0, negPoint.compareTo(otherNegPoint));
	}
	
	/**
	 * Tests equality when values equals
	 */
	@Test
	public void testEqualityEqual()
	{
		DataPoint point1 = new DataPoint(0.0, 0.0);
		DataPoint point2 = new DataPoint(null, null);
		DataPoint point3 = new DataPoint(10.0, -10.0);
		DataPoint point4 = new DataPoint(-9999999.92399, 999993921.3881);
		DataPoint point5 = new DataPoint(5.6, null);
		DataPoint point6 = new DataPoint(null, 28.3);
		
		DataPoint otherPoint1 = new DataPoint(0.0, 0.0);
		DataPoint otherPoint2 = new DataPoint(null, null);
		DataPoint otherPoint3 = new DataPoint(10.0, -10.0);
		DataPoint otherPoint4 = new DataPoint(-9999999.92399, 999993921.3881);
		DataPoint otherPoint5 = new DataPoint(5.6, null);
		DataPoint otherPoint6 = new DataPoint(null, 28.3);
		
		assertEquals(point1, point1);
		assertEquals(point1, otherPoint1);
		assertEquals(point2, otherPoint2);
		assertEquals(point3, otherPoint3);
		assertEquals(point4, otherPoint4);
		assertEquals(point5, otherPoint5);
		assertEquals(point6, otherPoint6);
	}
	
	/**
	 * Tests equality when values not equal
	 */
	@Test
	public void testEqualityNotEqual()
	{
		DataPoint point1 = new DataPoint(0.0, 0.0);
		DataPoint point2 = new DataPoint(null, null);
		DataPoint point3 = new DataPoint(10.0, -10.0);
		DataPoint point4 = new DataPoint(-9999999.92399, 999993921.3881);
		DataPoint point5 = new DataPoint(5.6, null);
		DataPoint point5Neg = new DataPoint(-5.6, null);
		DataPoint point6 = new DataPoint(null, 28.3);
		Integer wrongClass = new Integer(5);
		Double[] similar = new Double[]{10.0, -10.0};
		
		assertFalse(point1.equals(point2));
		assertFalse(point2.equals(point5));
		assertFalse(point2.equals(point6));
		assertFalse(point5.equals(point6));
		assertFalse(point3.equals(point4));
		assertFalse(point2.equals(point1));
		assertFalse(wrongClass.equals(point3));
		assertFalse(similar.equals(point3));
		assertFalse(point5.equals(point5Neg));
	}
	
	/**
	 * Tests swapping values
	 */
	@Test
	public void testSwap()
	{
		Double posValue1 = new Double(5.9);
		Double posValue2 = new Double(3.4);
		Double zero = new Double(0.0);
		Double negValue1 = new Double(-5.4);
		Double negValue2 = new Double(-3.3);
		DataPoint bothPositive = new DataPoint(posValue1, posValue2);
		DataPoint bothNegative = new DataPoint(negValue1, negValue2);
		DataPoint xNull = new DataPoint(null, posValue2);
		DataPoint yNull = new DataPoint(posValue1, null);
		DataPoint bothZero = new DataPoint(zero, zero);
		
		bothPositive.swap();
		bothNegative.swap();
		xNull.swap();
		yNull.swap();
		bothZero.swap();
		
		assertEquals(posValue1, bothPositive.getY());
		assertEquals(posValue2, bothPositive.getX());
		assertEquals(negValue1, bothNegative.getY());
		assertEquals(negValue2, bothNegative.getX());
		assertEquals(posValue2, xNull.getX());
		assertEquals(null, xNull.getY());
		assertEquals(null, yNull.getX());
		assertEquals(posValue1, yNull.getY());
		assertEquals(zero, bothZero.getX());
		assertEquals(zero, bothZero.getY());
	}
}
