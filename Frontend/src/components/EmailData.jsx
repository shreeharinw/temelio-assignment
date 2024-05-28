import React, { useState, useEffect } from 'react';
import { Dropdown } from 'primereact/dropdown';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import 'primereact/resources/themes/saga-blue/theme.css'; // Theme CSS
import 'primereact/resources/primereact.min.css'; // Core CSS
import axios from 'axios';
import './EmailData.css'; // Import your custom CSS file

const EmailData = ({ foundations }) => {
  const [selectedFoundation, setSelectedFoundation] = useState(null);
  const [emails, setEmails] = useState([]);

  useEffect(() => {
    if (selectedFoundation) {
      axios.get(`http://localhost:8080/api/emaildata/foundation/${selectedFoundation.id}`)
        .then(response => {
          setEmails(response.data);
        })
        .catch(error => {
          console.error('Error fetching emails:', error);
        });
    } else {
      setEmails([]);
    }
  }, [selectedFoundation]);

  return (
    <div className="email-data-container">
      <div className="dropdown-container">
        <Dropdown
          value={selectedFoundation}
          options={foundations.map(f => {
            return { name: f.email, id: f.id }
          })}
          onChange={(e) => setSelectedFoundation(e.value)}
          optionLabel="name"
          placeholder="Select Foundation"
          className="foundation-dropdown"
        />
      </div>
      <DataTable value={emails} className="emails-table">
        <Column field="id" header="ID" />
        <Column field="message" header="Message" />
        <Column header="Non-Profit Name" body={(rowData) => {
          return (
            <>
              {rowData.nonProfits.map((nonProfit, index) => (
                <div key={index}>{nonProfit.name}</div>
              ))}
            </>
          );
        }} />
        <Column header="Non-Profit Email" body={(rowData) => {
          return (
            <>
              {rowData.nonProfits.map((nonProfit, index) => (
                <div key={index}>{nonProfit.email}</div>
              ))}
            </>
          );
        }} />
        <Column header="Non-Profit Address" body={(rowData) => {
          return (
            <>
              {rowData.nonProfits.map((nonProfit, index) => (
                <div key={index}>{nonProfit.address}</div>
              ))}
            </>
          );
        }} />
      </DataTable>
    </div>
  );
};

export default EmailData;
