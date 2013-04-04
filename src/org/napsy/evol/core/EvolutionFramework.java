/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napsy.evol.core;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mhhf
 */
public class EvolutionFramework {
    
    private boolean debug1 = false;
    private boolean debug2 = false;
    
    public int individualSize = 1;
    public List population;
    
    public int a=10; // Populationsgröße
    public int b=5;  // Selektionsgröße
    public double p2 = 1; // Mutationswahrscheinlichkeit eines Individuums
    public double p3 = 0.8; // Mutationswahrscheinlichkeit eines Gens
   
    public Method iterationCall;
    private double bestFitness;
    
    public EvolutionFramework() {
        this.createInitialPopulation();
        
        this.calculateStats(1);
        this.evolve();
    }
    
    private void createInitialPopulation(){
        this.population = new ArrayList();
        
        // Generate populations
        for (int i = 0; i < a; i++) {
            double[] ind = new double[1];
            ind[0] = 20+Math.random()*50;
            this.population.add(ind);
        }
    }
    
    public double fitness(double[] ind) {
        //return Math.sqrt(ind[0]*ind[0]+1/(ind[0]*ind[0]));
        return Math.abs( Math.tan(ind[0])-ind[0] );
    }
    
    private void selection() {
        double[] fitArr = new double[population.size()];
        List toRemove = new ArrayList();
        
        for (int i = 0; i < population.size(); i++) {
            fitArr[i] = fitness((double[])population.get(i));
        }
        
        java.util.Arrays.sort(fitArr);
        if(debug1) System.out.println("\t\t"+java.util.Arrays.toString(fitArr));
        
        for (int i = 0; i < population.size(); i++) {
            if( fitness((double[])population.get(i)) > fitArr[b-1] ) {
               toRemove.add(population.get(i));
            }
        }
        
        for(Object rind: toRemove) {
           population.remove(rind);
        }
    }
    
    private void mutation(int t) {
        for(Object indiv:population) {
            if( Math.random() < p2 ) {
                double[] ind = (double[]) indiv;
                for (int i = 0; i < ind.length; i++) {
                    if(debug1) System.out.print("  Mutation "+ind[i]);
                    ind[i] *= 1.0+(Math.random()*2.0-1.0)*(1.0/(t*t));
                    if(debug1) System.out.println(" "+ind[i]);
                }
            }
        }
    }
    
    private void crossover() {
        int popSize;
        double[] ind1;
        double[] ind2;
        double[] indNew = new double[individualSize];
        
        // Solange die Population kleiner als die Populationsgröße ist
        while( (popSize = population.size()) < a ) {
            // Paare 2 Individuen zu einem neuen und füge diesen der Population hinzu
            int indI1 = (int)Math.round(Math.random()*(popSize-1));
            int indI2 = (int)Math.round(Math.random()*(popSize-1));
            while(indI2 == indI1) {
                indI2 = (int)Math.round(Math.random()*(popSize-1));
            }
            
            ind1 = (double[]) population.get(indI1);
            ind2 = (double[]) population.get(indI2);
            
            if(debug1) System.out.print("\t"+indI1+" fucks "+indI2+"\t");
            for (int i = 0; i < ind1.length; i++) {
                double r = Math.random()*0.8+0.1;
                indNew[i] = ind1[i]*r+ind2[i]*(1-r);
                if(debug1) System.out.print(ind1[i]+" "+ind2[i]+" "+indNew[i]);
            }
            if(debug1) System.out.print("\n");
            
            population.add(indNew);
        }
    }
    
    private void calculateStats(int c) {
        
        double best=999;
        double sum=0;
        double worst=0;
        
        for (Object ind: population) {
            double[] i=(double[]) ind;
            double fit = fitness(i);
            if(fit < best) {
                best = fit;
            } else if(fit > worst) {
                worst = fit;
            }
            sum += fit;
        } 
        if(debug2) System.out.println("\tGeneration "+c+" best: "+best+" avarage: "+(sum/population.size())+" worst: "+worst);
        bestFitness = best;
    }
    
    private void evolve() {
        int populationCounter = 0;
        int generations = 200;
        bestFitness = 10;
        double best[] = new double[generations];
        
        while( populationCounter<generations  ) {
            populationCounter++;
            if(debug2) System.out.println("Begin a New Iteration");
            selection();
            if(debug2) System.out.println("\tnew Population Size: "+population.size());
            crossover();
            mutation(populationCounter);
            
            calculateStats(populationCounter);
            best[populationCounter-1] = bestFitness;
        }
        System.out.println(bestFitness);
        //System.out.println("\n\n"+java.util.Arrays.toString(best));
    }
}
