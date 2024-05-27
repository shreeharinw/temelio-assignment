import React, { useState, useEffect } from 'react';
import { TabView, TabPanel } from 'primereact/tabview';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import 'primereact/resources/themes/saga-blue/theme.css'; // Theme CSS
import 'primereact/resources/primereact.min.css'; // Core CSS
import axios from 'axios';
import AddData from './AddData';
import { Button } from 'primereact/button';
import NonProfits from './NonProfits';
import EmailData from './EmailData';

const Main = () => {
  const [foundations, setFoundations] = useState([]);
  const [nonProfits, setNonProfits] = useState([]);
  const [displayAddData, setDisplayAddData] = useState(false);

  useEffect(() => {
    // Fetch foundations data
    axios.get('http://localhost:8080/api/foundations')
      .then(response => {
        setFoundations(response.data);
      })
      .catch(error => {
        console.error('Error fetching foundations:', error);
      });

    // Fetch non-profits data
    axios.get('http://localhost:8080/api/nonprofits')
      .then(response => {
        setNonProfits(response.data);
      })
      .catch(error => {
        console.error('Error fetching non-profits:', error);
      });
  }, [displayAddData]);

  return (
    <div className="container">
      <div className="p-mb-3">
        <Button label="Add Data" className="p-button-primary" onClick={() => setDisplayAddData(true)} />
      </div>
      <TabView>
        <TabPanel header="Foundations">
          <DataTable value={foundations} className="p-datatable-striped">
            <Column field="id" header="ID" />
            <Column field="email" header="Email" />
            {/* Add more columns as needed */}
          </DataTable>
        </TabPanel>
        <TabPanel header="Non-Profits">
          {/* <DataTable value={nonProfits} className="p-datatable-striped">
            <Column field="id" header="ID" />
            <Column field="name" header="Name" />
            <Column field="address" header="Address" />
            <Column field="email" header="Email" />
          </DataTable> */}
          <NonProfits foundations={foundations} nonProfits={nonProfits}/>
        </TabPanel>
        <TabPanel header="Emails">
          {/* <DataTable value={nonProfits} className="p-datatable-striped">
            <Column field="id" header="ID" />
            <Column field="name" header="Name" />
            <Column field="address" header="Address" />
            <Column field="email" header="Email" />
          </DataTable> */}
          <EmailData foundations={foundations}/>
        </TabPanel>
      </TabView>
      {/* AddData modal */}
      <AddData visible={displayAddData} onHide={() => setDisplayAddData(false)} />
    </div>
  );
};

export default Main;
